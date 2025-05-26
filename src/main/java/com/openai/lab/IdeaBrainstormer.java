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

public class IdeaBrainstormer {

    // Define o ID do modelo da OpenAI a ser utilizado.
    // 'gpt-4o-mini' é uma boa escolha para geração de ideias.
    private static final String MODEL_ID = "gpt-4o-mini"; // Seu modelo especificado

    // Caminho para o arquivo que contém o prompt de brainstorming.
    private static final String PROMPT_FILE_PATH = "prompts/brainstorming_ideias.txt";

    public static void main(String[] args) {
//************************** Validacion del formato UTF-8 DEL iDE *******************************
 
                try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        } catch (java.io.UnsupportedEncodingException e) {
            System.err.println("UTF-8 encoding is not supported: " + e.getMessage());
            return;
        }
//****************************************************************************************** */


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

        System.out.println("-------------------------------------------------");
        System.out.println("  Laboratório de Brainstorming de Ideias c/ OpenAI");
        System.out.println("-------------------------------------------------");

        try {
            // 1. Lê todo o conteúdo do arquivo de prompt.
            // É crucial especificar UTF-8 para garantir a correta leitura de caracteres especiais.
            String brainstormingPrompt = Files.readString(Paths.get(PROMPT_FILE_PATH), StandardCharsets.UTF_8);

            System.out.println("Instrução para Brainstorming: " + brainstormingPrompt);
            System.out.println("\nGerando ideias. Isso pode levar alguns segundos...");

            // 2. Constrói a lista de mensagens para a API de Chat.
            List<ChatMessage> messages = new ArrayList<>();
            // Mensagem do sistema: Define o papel do assistente de IA.
            messages.add(new ChatMessage("system", "Você é um gerador de ideias criativo e inovador para projetos de software."));
            // Mensagem do usuário: Envia o prompt lido do arquivo.
            messages.add(new ChatMessage("user", brainstormingPrompt));

            // 3. Constrói a requisição de conclusão de chat.
            ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                    .model(MODEL_ID)
                    .messages(messages)
                    .temperature(0.8) // Um valor mais alto para incentivar mais criatividade nas ideias.
                    .maxTokens(500)   // Aumente o limite de tokens para permitir mais ideias e descrições.
                    .build();

            // 4. Realiza a chamada à API da OpenAI.
            List<ChatCompletionChoice> choices = service.createChatCompletion(chatCompletionRequest).getChoices();

            // 5. Extrai o conteúdo das ideias geradas.
            String generatedIdeas = choices.get(0).getMessage().getContent();

            System.out.println("\n-------------------------------------------------");
            System.out.println("Ideias Geradas:");
            System.out.println(generatedIdeas);
            System.out.println("-------------------------------------------------");

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de prompt: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro ao interagir com a API da OpenAI: " + e.getMessage());
            // Para depuração de erros como cota excedida.
            // if (e.getMessage() != null && e.getMessage().contains("quota")) {
            //     System.err.println("Por favor, verifique sua conta OpenAI para créditos ou plano de faturamento.");
            // }
        } finally {
            // Garante que o ExecutorService do OpenAiService seja desligado para liberar recursos.
            service.shutdownExecutor();
        }
    }
}
