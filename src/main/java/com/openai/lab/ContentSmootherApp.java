package com.openai.lab;

// ========================================================================
// IMPORTAÇÕES ESPECÍFICAS PARA A BIBLIOTECA 'com.theokanning.openai-gpt4-java'
// Essas são as importações essenciais para interagir com a API da OpenAI.
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.theokanning.openai.completion.chat.ChatCompletionChoice; // Para carregar a chave da API de um arquivo .env
import com.theokanning.openai.completion.chat.ChatCompletionRequest; // Necessário para configurar o tempo limite (timeout) do OpenAiService
import com.theokanning.openai.completion.chat.ChatMessage; // Necessário para criar uma lista mutável de ChatMessage
import com.theokanning.openai.service.OpenAiService;

import io.github.cdimascio.dotenv.Dotenv;

public class ContentSmootherApp {


    private static final String MODEL_ID = "gpt-4o-mini"; //Meu Modelo

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

        // Verifica se a chave da API foi configurada corretamente
        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("Erro: A variável de ambiente 'OPENAI_API_KEY' não está configurada.");
            System.err.println("Por favor, crie um arquivo '.env' na raiz do projeto com OPENAI_API_KEY=sua_chave_aqui");
            return; // Encerra o programa se a chave não estiver presente
        }

        // Inicializa o serviço da OpenAI. É uma boa prática definir um tempo limite (timeout)
        // para as requisições, evitando que o programa fique travado indefinidamente.
        OpenAiService service = new OpenAiService(apiKey, Duration.ofSeconds(60));

        // Inicializa o Scanner para ler a entrada do usuário no console
        Scanner scanner = new Scanner(System.in);

        // Mensagens de boas-vindas e instrução para o usuário
        System.out.println("------------------------------------------------------------------");
        System.out.println("  Laboratório de Suavização de Conteúdo com OpenAI em Java        ");
        System.out.println("------------------------------------------------------------------");
        System.out.println("Digite uma frase que você gostaria de 'suavizar' (ou 'sair' para encerrar):");

        String userInput;
        // Loop principal para interagir com o usuário
        while (true) {
            System.out.print("\nSua frase (original): ");
            userInput = scanner.nextLine(); // Lê a frase digitada pelo usuário

            // Verifica se o usuário deseja sair do programa
            if (userInput.equalsIgnoreCase("sair")) {
                System.out.println("Encerrando o programa.");
                break; // Sai do loop
            }

            System.out.println("Processando a frase para suavização...");

            try {
                // Constrói a lista de mensagens para a conversa com o modelo de chat da OpenAI.
                // A API de Chat da OpenAI usa um formato de conversa (turnos de 'system', 'user', 'assistant').
                List<ChatMessage> messages = new ArrayList<>();

                // Mensagem do sistema: Define o papel ou a personalidade do assistente.
                // Isso ajuda a guiar o comportamento geral do modelo.
                messages.add(new ChatMessage("system", "Você é um assistente que reescreve textos para torná-los mais polidos e menos diretos, mantendo a intenção original."));

                // Mensagem do usuário: Contém a instrução específica para a tarefa
                // e a frase que o usuário forneceu para ser suavizada.
                messages.add(new ChatMessage("user", "Reescreva a seguinte frase de forma a torná-la mais suave, menos ofensiva ou menos direta, mas mantendo o significado original. Se a frase já for neutra, apenas a retorne como está.\n\nFrase original: " + userInput));

                // Constrói a requisição de conclusão de chat.
                // Isso inclui o modelo a ser usado, as mensagens da conversa, e parâmetros como temperatura e max_tokens.
                ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                        .model(MODEL_ID)      // O modelo da OpenAI a ser usado
                        .messages(messages)   // A lista de mensagens que compõem a conversa
                        .temperature(0.7)     // Controla a criatividade/aleatoriedade da resposta (0.0 a 1.0)
                                              // Um valor de 0.7 permite alguma variação, mas mantém a coerência.
                        .maxTokens(150)       // Limita o número de tokens (palavras/partes de palavras) na resposta,
                                              // para evitar respostas excessivamente longas.
                        .build();

                // Realiza a chamada à API da OpenAI e obtém a lista de escolhas (respostas).
                // O modelo pode retornar múltiplas escolhas, mas para esta aplicação, pegamos a primeira.
                List<ChatCompletionChoice> choices = service.createChatCompletion(chatCompletionRequest).getChoices();

                // Extrai o conteúdo do texto suavizado da primeira escolha da resposta do modelo.
                String smoothedContent = choices.get(0).getMessage().getContent();

                System.out.println("Frase suavizada: " + smoothedContent);

            } catch (Exception e) {
                // Captura e imprime quaisquer erros que ocorram durante a interação com a API da OpenAI.
                // Isso pode incluir problemas de rede, erros de autenticação, ou erros de cota excedida.
                System.err.println("Erro ao interagir com a API da OpenAI: " + e.getMessage());
                // Você pode adicionar lógica mais específica aqui para diferentes tipos de erros.
                // Por exemplo, para o erro de cota que você teve anteriormente:
                // if (e.getMessage() != null && e.getMessage().contains("quota")) {
                //     System.err.println("Por favor, verifique sua conta OpenAI para créditos ou plano de faturamento.");
                // }
            }
        }
        scanner.close(); // Fecha o Scanner para liberar recursos
        // É CRUCIAL chamar shutdownExecutor() quando você terminar de usar o OpenAiService.
        // Ele encerra os threads internos e libera recursos de rede, prevenindo vazamentos de recursos
        // em aplicações de longa duração.
        service.shutdownExecutor();
    }
}