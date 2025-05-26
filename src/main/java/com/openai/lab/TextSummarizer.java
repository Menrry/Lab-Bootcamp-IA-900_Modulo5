package com.openai.lab;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

import io.github.cdimascio.dotenv.Dotenv;

// Troque esta linha:
//String promptContent = Files.readString(Paths.get(PROMPT_FILE_PATH));

// Por esta: String promptContent = Files.readString(Paths.get(PROMPT_FILE_PATH), StandardCharsets.UTF_8);

// ...existing code...
//*************************************************************************** */

public class TextSummarizer {

    // Define o ID do modelo da OpenAI a ser utilizado.
    // 'gpt-4o-mini' é uma opção recente e eficiente para tarefas de texto.
    private static final String MODEL_ID = "gpt-4o-mini"; // Seu modelo especificado

    // Caminho para o arquivo que contém a instrução e o texto a ser resumido.
    private static final String PROMPT_FILE_PATH = "prompts/summary.txt";

    public static void main(String[] args) {
        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        } catch (java.io.UnsupportedEncodingException e) {
            System.err.println("UTF-8 encoding is not supported: " + e.getMessage());
            return;
        }

        /*
         * 
         * 
         * 
         * // ...existing code...
public static void main(String[] args) {
    System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
// ...existing code...
         */
        // Carrega as variáveis de ambiente do arquivo .env
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("OPENAI_API_KEY");

        // Verifica se a chave da API foi configurada corretamente.
        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("Erro: A variável de ambiente 'OPENAI_API_KEY' não está configurada.");
            System.err.println("Por favor, crie um arquivo '.env' na raiz do projeto com OPENAI_API_KEY=sua_chave_aqui");
            return;
        }

        // Inicializa o serviço da OpenAI com a chave da API e um tempo limite.
        OpenAiService service = new OpenAiService(apiKey, Duration.ofSeconds(60));

        System.out.println("----------------------------------------------");
        System.out.println("  Laboratório de Resumo de Texto com OpenAI   ");
        System.out.println("----------------------------------------------");

        try {
            // 1. Lê todo o conteúdo do arquivo de prompt.
            //String promptContent = Files.readString(Paths.get(PROMPT_FILE_PATH));
            String promptContent = Files.readString(Paths.get(PROMPT_FILE_PATH), StandardCharsets.UTF_8);

            // 2. Divide o conteúdo em instrução e texto longo usando o separador "---".
            // O limite de 2 garante que, se houver múltiplos "---", ele divida apenas no primeiro.
            String[] parts = promptContent.split("---", 2);

            if (parts.length < 2) {
                System.err.println("Erro: O arquivo '" + PROMPT_FILE_PATH + "' não está formatado corretamente.");
                System.err.println("Ele deve conter uma linha com '---' para separar a instrução do texto.");
                return;
            }

            String instruction = parts[0].trim(); // A parte antes do "---" é a instrução.
            String longText = parts[1].trim();    // A parte depois do "---" é o texto longo.

            System.out.println("Instrução de resumo: " + instruction);
            System.out.println("Texto a ser resumido (primeiras 100 caracteres): " + longText.substring(0, Math.min(longText.length(), 100)) + "...");
            System.out.println("\nProcessando resumo...");

            // 3. Constrói a lista de mensagens para a API de Chat.
            List<ChatMessage> messages = new ArrayList<>();
            // Mensagem do sistema: Define o papel do assistente de IA.
            messages.add(new ChatMessage("system", "Você é um assistente de resumo de texto profissional."));
            // Mensagem do usuário: Combina a instrução e o texto longo para o modelo.
            messages.add(new ChatMessage("user", instruction + "\n\n" + longText));

            // 4. Constrói a requisição de conclusão de chat.
            ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                    .model(MODEL_ID)
                    .messages(messages)
                    .temperature(0.5) //0.5 inicialmente// Um pouco menos de criatividade para resumos mais diretos.
                    .maxTokens(200)   // Define um limite de tokens para o resumo gerado.
                                      // Ajuste conforme o comprimento desejado do resumo.
                    .build();

            // 5. Realiza a chamada à API da OpenAI.
            List<ChatCompletionChoice> choices = service.createChatCompletion(chatCompletionRequest).getChoices();

            // 6. Extrai o conteúdo do resumo da resposta do modelo.
            String summary = choices.get(0).getMessage().getContent();

            System.out.println("\n----------------------------------------------");
            System.out.println("Resumo Gerado:");
            System.out.println(summary);
            System.out.println("----------------------------------------------");

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de prompt: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro ao interagir com a API da OpenAI: " + e.getMessage());
            // Para depuração de erros de cota, etc.
            // if (e.getMessage() != null && e.getMessage().contains("quota")) {
            //     System.err.println("Por favor, verifique sua conta OpenAI para créditos ou plano de faturamento.");
            // }
        } finally {
            // Garante que o ExecutorService do OpenAiService seja desligado.
            service.shutdownExecutor();
        }
    }
}