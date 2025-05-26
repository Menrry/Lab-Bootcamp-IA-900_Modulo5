package com.openai.lab;

// ========================================================================
// Importações comuns para projetos Java, que o Copilot frequentemente sugere.
// Copilot pode autocompletar estas linhas após você digitar a primeira classe.
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage; // Exemplo de importação que o Copilot pode sugerir ao usar delay.
import com.theokanning.openai.service.OpenAiService;

import io.github.cdimascio.dotenv.Dotenv;

// ========================================================================
// Cenário de Uso do Copilot: Sugestão de Javadoc
// Ao digitar "/**" acima de uma classe ou método, o Copilot frequentemente gera um Javadoc completo.
/**
 * Esta classe demonstra como o GitHub Copilot pode auxiliar no desenvolvimento de código Java,
 * usando exemplos baseados nos programas de interação com a API da OpenAI já desenvolvidos.
 * Aborda cenários de uso, benefícios e limitações do Copilot.
 */
public class CopilotAssistanceDemo {

    // Cenário de Uso do Copilot: Sugestão de constantes e variáveis.
    // Ao digitar "private static final String MODEL_ID = ", o Copilot pode sugerir o valor
    // baseado em usos anteriores ou contexto de projetos similares.
    private static final String MODEL_ID = "gpt-4o-mini"; // Usado nos exemplos anteriores.

    // Cenário de Uso do Copilot: Autocompletar nomes de arquivos/caminhos.
    // Ao digitar "private static final String PROMPT_FILE_PATH = "prompts/", o Copilot pode
    // listar arquivos existentes na pasta "prompts/".
    private static final String PROMPT_FILE_PATH_SUMMARY = "prompts/summary.txt";
    private static final String PROMPT_FILE_PATH_BRAINSTORMING = "prompts/brainstorming_ideias.txt";

    public static void main(String[] args) {
        // ========================================================================
        // FORÇAR ENCODING DE SAÍDA NO JAVA: SOLUÇÃO PARA CARACTERES ESPECIAIS NO CONSOLE
        // Este bloco de código foi adicionado para garantir que o console do Windows
        // (CMD ou PowerShell) exiba corretamente caracteres UTF-8.
        // Copilot pode ser útil para sugerir try-catch blocks para IOExceptions.
        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        } catch (java.io.UnsupportedEncodingException e) {
            System.err.println("UTF-8 encoding is not supported: " + e.getMessage());
            return;
        }
        // ========================================================================

        System.out.println("---------------------------------------------------------");
        System.out.println("  Demonstração de Assistência do GitHub Copilot em Java  ");
        System.out.println("---------------------------------------------------------");
        System.out.println("\nEste programa simula cenários de desenvolvimento com o Copilot.");

        // Cenário de Uso do Copilot: Carregamento de variáveis de ambiente.
        // Ao digitar "Dotenv dotenv = Dotenv.load();", o Copilot pode sugerir a linha seguinte.
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("OPENAI_API_KEY");

        // Cenário de Uso do Copilot: Geração de blocos de validação.
        // Ao digitar "if (apiKey == null || apiKey.isEmpty()) {", o Copilot pode prever
        // o corpo do if para tratamento de erro de chave.
        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("\nErro: A variável de ambiente 'OPENAI_API_KEY' não está configurada.");
            System.err.println("Por favor, crie um arquivo '.env' na raiz do projeto com OPENAI_API_KEY=sua_chave_aqui");
            return;
        }

        // Cenário de Uso do Copilot: Inicialização de serviços.
        // Ao digitar "OpenAiService service = new OpenAiService(apiKey,", o Copilot pode
        // completar com "Duration.ofSeconds(60));" baseado em padrões de uso.
        OpenAiService service = new OpenAiService(apiKey, Duration.ofSeconds(60));

        try {
            System.out.println("\n--- Cenário 1: Autocompletar e Geração de Código Repetitivo (Resumo) ---");
            // Cenário: Gerar código para ler um arquivo e processar texto.
            // Copilot é ótimo para loops de leitura de arquivo, divisão de strings.
            System.out.println("Lendo prompt de resumo de: " + PROMPT_FILE_PATH_SUMMARY);
            String summaryPromptContent = Files.readString(Paths.get(PROMPT_FILE_PATH_SUMMARY), StandardCharsets.UTF_8);
            String[] summaryParts = summaryPromptContent.split("---", 2);
            if (summaryParts.length < 2) {
                System.err.println("Erro: Formato inválido no arquivo de resumo.");
                return;
            }
            String instructionSummary = summaryParts[0].trim();
            String longTextSummary = summaryParts[1].trim();

            // Cenário de Uso do Copilot: Construção de requisição da API.
            // Ao digitar "List<ChatMessage> messages = new ArrayList<>();" e começar a adicionar,
            // o Copilot pode prever as mensagens de sistema e usuário.
            List<ChatMessage> messagesSummary = new ArrayList<>();
            messagesSummary.add(new ChatMessage("system", "Você é um assistente de resumo de texto profissional."));
            messagesSummary.add(new ChatMessage("user", instructionSummary + "\n\n" + longTextSummary));

            // Copilot pode sugerir o builder pattern para ChatCompletionRequest.
            ChatCompletionRequest chatCompletionRequestSummary = ChatCompletionRequest.builder()
                    .model(MODEL_ID)
                    .messages(messagesSummary)
                    .temperature(0.5)
                    .maxTokens(200)
                    .build();

            System.out.println("Chamando OpenAI para resumo...");
            // Thread.sleep(2000); // Para simular um atraso da API, Copilot pode sugerir Sleep.

            // Cenário de Uso do Copilot: Extração de resultado.
            // Após chamar 'service.createChatCompletion', o Copilot pode sugerir '.getChoices().get(0).getMessage().getContent()'.
            String summaryResult = service.createChatCompletion(chatCompletionRequestSummary).getChoices().get(0).getMessage().getContent();
            System.out.println("Resumo Gerado: " + summaryResult.substring(0, Math.min(summaryResult.length(), 150)) + "...");

            System.out.println("\n--- Cenário 2: Geração de Funções Utilitárias e Estruturas de Dados ---");
            // Cenário: Geração de método auxiliar para logging ou formatação.
            // Ao começar a digitar um método como 'private static void printSectionHeader',
            // o Copilot pode sugerir todo o corpo do método.
            printSectionHeader("Brainstorming de Ideias");

            // Cenário de Uso do Copilot: Código para leitura de outro prompt.
            // Similar ao cenário 1, mas com um prompt diferente.
            System.out.println("Lendo prompt de brainstorming de: " + PROMPT_FILE_PATH_BRAINSTORMING);
            String brainstormingPrompt = Files.readString(Paths.get(PROMPT_FILE_PATH_BRAINSTORMING), StandardCharsets.UTF_8);

            List<ChatMessage> messagesBrainstorming = new ArrayList<>();
            messagesBrainstorming.add(new ChatMessage("system", "Você é um gerador de ideias criativo e inovador."));
            messagesBrainstorming.add(new ChatMessage("user", brainstormingPrompt));

            ChatCompletionRequest chatCompletionRequestBrainstorming = ChatCompletionRequest.builder()
                    .model(MODEL_ID)
                    .messages(messagesBrainstorming)
                    .temperature(0.8)
                    .maxTokens(800)
                    .build();

            System.out.println("Chamando OpenAI para gerar ideias...");
            String ideasResult = service.createChatCompletion(chatCompletionRequestBrainstorming).getChoices().get(0).getMessage().getContent();
            System.out.println("Ideias Geradas (início): " + ideasResult.substring(0, Math.min(ideasResult.length(), 250)) + "...");


        } catch (IOException e) {
            System.err.println("\nErro de I/O: " + e.getMessage());
            System.err.println("Verifique se os arquivos de prompt existem em 'prompts/' e se há permissão de leitura.");
        } catch (Exception e) {
            System.err.println("\nErro ao interagir com a API da OpenAI: " + e.getMessage());
            System.err.println("Verifique sua chave de API e o status da sua cota na plataforma OpenAI.");
        } finally {
            // Cenário de Uso do Copilot: Bloco finally para limpeza de recursos.
            // Ao digitar 'finally {', o Copilot pode sugerir 'service.shutdownExecutor();'.
            // CORREÇÃO AQUI: De service.println para System.out.println
            System.out.println("\n---------------------------------------------------------");
            System.out.println("Demonstração concluída. Recursos da OpenAI liberados.");
            System.out.println("---------------------------------------------------------");
            service.shutdownExecutor(); // Garante o fechamento do serviço
        }

        // ========================================================================
        // DISCUSSÃO SOBRE A ASSISTÊNCIA DO COPILOT
        // ========================================================================
        System.out.println("\n\n=== Reflexões sobre o GitHub Copilot em Java ===");

        System.out.println("\n1. Cenários onde o Copilot foi mais útil:");
        System.out.println("   - **Autocompletar código repetitivo:** Extremamente eficiente para importações, inicialização de objetos (ex: Dotenv, OpenAiService), e a estrutura básica de requisições da API OpenAI (builders, .build()).");
        System.out.println("   - **Geração de boilerplate:** Criação de getters/setters, construtores, e métodos equals/hashCode. Embora não usados diretamente neste exemplo, são rotinas comuns em Java.");
        System.out.println("   - **Sugestão de padrões de uso de bibliotecas:** Dada a repetitividade da chamada à API OpenAI (`service.createChatCompletion(...).getChoices().get(0).getMessage().getContent()`), o Copilot aprende rapidamente e sugere a linha completa.");
        System.out.println("   - **Completar estruturas de controle:** Condicionais (`if`), loops (`while`), e blocos `try-catch-finally` são frequentemente completados com a estrutura básica ou sugestões de tratamento de exceções.");
        System.out.println("   - **Geração de Javadoc e comentários:** Ao digitar `/**`, o Copilot frequentemente gera uma documentação inicial útil para classes e métodos.");
        System.out.println("   - **Testes Unitários (fora deste escopo):** Para testes JUnit, o Copilot pode sugerir asserts e mocks baseados no código a ser testado.");

        System.out.println("\n2. Como o Copilot pode acelerar o desenvolvimento e reduzir erros:");
        System.out.println("   - **Aceleração:** Reduz drasticamente a digitação de código repetitivo e permite que o desenvolvedor se concentre mais na lógica de negócio complexa, delegando o boilerplate à IA.");
        System.out.println("   - **Redução de Erros:** Ao sugerir padrões de API e completar linhas, diminui a chance de erros de digitação, nomes de métodos incorretos ou esquecimento de parâmetros comuns.");
        System.out.println("   - **Descoberta de API:** Pode sugerir métodos ou classes de uma biblioteca que o desenvolvedor não conhece bem, atuando como uma espécie de 'intelliSense' avançado.");
        System.out.println("   - **Melhores Práticas:** Às vezes, sugere implementações que seguem padrões ou melhores práticas da comunidade Java.");

        System.out.println("\n3. Limitações ou desafios encontrados no ambiente Java (e neste projeto):");
        System.out.println("   - **Lógica de Negócio Muito Específica:** Para a lógica interna do `ContentSmootherApp` ou `TextSummarizer` (ex: a instrução exata para a IA ou como dividir o prompt do texto), o Copilot pode não ser tão útil quanto em tarefas mais genéricas.");
        System.out.println("   - **Dependência de Contexto:** A qualidade das sugestões depende muito do contexto do código já escrito. Em um arquivo novo ou em uma área de código muito singular, as sugestões podem ser menos relevantes.");
        System.out.println("   - **Alucinações de Código:** Ocasionalmente, o Copilot pode sugerir código que não compila, usa classes inexistentes ou implementa uma lógica incorreta, exigindo revisão cuidadosa.");
        System.out.println("   - **Sugestões Redundantes/Ineficientes:** Às vezes, sugere código mais longo ou menos eficiente do que uma solução mais concisa ou idiomática.");
        System.out.println("   - **Configuração de Ambiente:** Configurações complexas de Maven ou sistema de arquivos (como o problema de `file.encoding` ou caminhos de prompt) não são áreas onde o Copilot se destaca, pois são mais sobre o ambiente de execução do que a lógica de código Java em si.");
        System.out.println("   - **Privacidade de Código:** Em ambientes corporativos, há preocupações sobre a privacidade do código que está sendo enviado para os servidores do Copilot para gerar sugestões.");

        System.out.println("\nEm resumo, o Copilot é uma ferramenta poderosa para otimizar a codificação repetitiva e acelerar o desenvolvimento, mas não substitui o conhecimento e a revisão humana.");
    }

    // Cenário de Uso do Copilot: Geração de métodos auxiliares.
    // Ao digitar "private static void printSectionHeader(String title) {",
    // o Copilot pode preencher o corpo do método com uma formatação padrão.
    /**
     * Imprime um cabeçalho de seção formatado no console.
     * @param title O título da seção.
     */
    private static void printSectionHeader(String title) {
        System.out.println("\n--- " + title.toUpperCase() + " ---");
    }
}