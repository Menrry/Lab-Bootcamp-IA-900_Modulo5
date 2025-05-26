package com.openai.lab;

// ========================================================================
// IMPORTACIONES ESPECÍFICAS PARA com.theokanning.openai-gpt3-java
// Estas son las importaciones clave para la interacción con la API de OpenAI.
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.theokanning.openai.completion.chat.ChatCompletionChoice; // Para cargar la API Key desde un archivo .env
import com.theokanning.openai.completion.chat.ChatCompletionRequest; // Necesario para configurar el timeout de OpenAiService
import com.theokanning.openai.completion.chat.ChatMessage; // Necesario para crear la lista mutable de ChatMessage
import com.theokanning.openai.service.OpenAiService;

import io.github.cdimascio.dotenv.Dotenv;

public class ContentMelhor {

    private static final String MODEL_ID = "gpt-4o-mini"; //Meu Modelo

    public static void main(String[] args) {
        // Cargar variables de entorno desde .env
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("OPENAI_API_KEY");

        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("Erro: A variável de ambiente 'OPENAI_API_KEY' não está configurada.");
            System.err.println("Por favor, crie um arquivo '.env' na raiz do projeto com OPENAI_API_KEY=sua_chave_aqui");
            return;
        }

        // Inicialización de OpenAiService. Se recomienda establecer un timeout.
        OpenAiService service = new OpenAiService(apiKey, Duration.ofSeconds(60));

        Scanner scanner = new Scanner(System.in);

        System.out.println("------------------------------------------------------------------");
        System.out.println("  Laboratório de Suavização de Conteúdo com OpenAI em Java        ");
        System.out.println("------------------------------------------------------------------");
        System.out.println("Digite uma frase que você gostaria de 'suavizar' (ou 'sair' para encerrar):");

        String userInput;
        while (true) {
            System.out.print("\nSua frase (original): ");
            userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("sair")) {
                System.out.println("Encerrando o programa.");
                break;
            }

            System.out.println("Processando a frase para suavización...");

            try {
                // Construcción de los mensajes para la conversación con el modelo
                List<ChatMessage> messages = new ArrayList<>();
                // Mensaje del sistema para dar un rol al asistente
                messages.add(new ChatMessage("system", "Você é um assistente que reescreve textos para torná-los mais polidos e menos diretos, mantendo a intenção original."));
                // Mensaje del usuario con la instrucción y la frase a suavizar
                messages.add(new ChatMessage("user", "Reescreva a seguinte frase de forma a torná-la mais suave, menos ofensiva ou menos direta, mas mantendo o significado original. Se a frase já for neutra, apenas a retorne como está.\n\nFrase original: " + userInput));

                // Construcción de la solicitud de Chat Completion
                ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                        .model(MODEL_ID)
                        .messages(messages)
                        .temperature(0.7) // Controla la creatividad de la respuesta (0.0-1.0)
                        .maxTokens(150)   // Limita el número de tokens en la respuesta
                        .build();

                // Llamada a la API de OpenAI para obtener la respuesta de chat
                List<ChatCompletionChoice> choices = service.createChatCompletion(chatCompletionRequest).getChoices();

                // Extrae el contenido del mensaje de la primera elección
                String smoothedContent = choices.get(0).getMessage().getContent();

                System.out.println("Frase suavizada: " + smoothedContent);

            } catch (Exception e) {
                // Manejo de errores generales. La API de OpenAI puede lanzar excepciones por varios motivos.
                System.err.println("Erro ao interagir com a API da OpenAI: " + e.getMessage());
                // Puedes añadir lógica específica aquí para diferentes tipos de errores si es necesario.
                // Por ejemplo, para el error de cuota que tuviste:
                // if (e.getMessage() != null && e.getMessage().contains("quota")) {
                //     System.err.println("Por favor, verifique sua conta OpenAI para créditos ou plano de faturamento.");
                // }
            }
        }
        scanner.close();
        // Es crucial apagar el ExecutorService de OpenAiService cuando ya no lo necesites
        // para liberar recursos. Esto es especialmente importante en aplicaciones de larga duración.
        service.shutdownExecutor();
    }
}