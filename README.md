# Laboratório OpenAI & GitHub Copilot: Exploração e Aplicações Práticas com Java

Este repositório serve como um **laboratório prático** para explorar as capacidades das **ferramentas da OpenAI**, com um foco especial nos **filtros de conteúdo** e na **criação assistida por inteligência artificial**, aplicando os conceitos em um ambiente Java. Também demonstraremos como o GitHub Copilot pode potencializar a produtividade e a qualidade do código.

---

## 🚀 Objetivo do Laboratório

* **Aplicar conceitos:** Colocar em prática os conhecimentos sobre os modelos de linguagem da OpenAI e as funcionalidades do Copilot em um contexto Java.
* **Compreender filtros de conteúdo:** Investigar como os filtros de conteúdo da OpenAI funcionam e como podem ser aplicados para garantir a segurança e a conformidade em aplicações Java.
* **Dominar criação assistida:** Experimentar diferentes técnicas de prompt engineering para gerar conteúdo de alta qualidade com assistência de IA.
* **Documentar processos:** Registrar de forma clara e estruturada os experimentos, prompts utilizados, resultados e aprendizados.

---

## 📦 Estrutura do Repositório

* `prompts/`: Diretório contendo os prompts utilizados em cada experimento.
* `src/main/java/com/openai/lab/`: Diretório para os exemplos de código Java que interagem com a API da OpenAI.
* `readme/`: Anotações detalhadas sobre os aprendizados e desafios encontrados.

---

## 🛠️ Pré-requisitos

* **Conta OpenAI** com acesso à API.
* **Chave de API da OpenAI** (configurada como variável de ambiente ou de forma segura).
* **GitHub Copilot** instalado e configurado no seu IDE (IntelliJ IDEA, VS Code com extensão Java, etc.).
* **Java Development Kit (JDK) 11 ou superior**.
* **Maven** ou **Gradle** para gerenciamento de dependências.

---

## 💻 Configuração do Ambiente

1.  **Clone este repositório:**
    ```
      git clone https://github.com/Menrry/Lab-Bootcamp-IA-900_Modulo5.git
      cd Lab-Bootcamp-IA-900_Modulo5
    ```    
2.  **Configure sua chave de API da OpenAI:**
O local exato onde você deve verificar suas chaves de API OpenAI (criá-las, visualizá-las e gerenciá-las) é:

    ```   
       https://platform.openai.com/api-keys
    ```  

* Esta é a seção "Chaves de API" nas configurações da sua organização ou conta.

* É importante que você use uma chave do tipo "Chave Secreta" e que comece com sk-. Se você tiver uma chave em um formato diferente (como org-), essa não é a chave que você deve usar para autenticar diretamente na API.

### **Quando estiver nessa página, você pode:**

* **Crie uma nova chave secreta:** se você não tiver uma ou a que você está usando não funcionar mais.
* **Copiar uma chave existente:** certifique-se de copiá-la corretamente (ela só é exibida uma vez por 
  completo quando você a cria, depois apenas o primeiro e o último caracteres).
* **Excluir chaves:** se você acredita que uma chave foi comprometida.
* **Depois de ter sua sk-key, certifique-se de colocá-la corretamente no seu arquivo .env:**
    **OPENAI_API_KEY=**sk-SUA_CHAVE_AQUI

* **A forma mais segura é configurar sua chave como uma **variável de ambiente**.** É usar um arquivo **`.env`** com a biblioteca **`dotenv-java`** (adicione `com.github.cdimascio:dotenv-java:2.2.0` como dependência no Maven), **você pode fazer:**

* **Quando você adiciona .env** ao seu arquivo .gitignore, esta linha informa ao Git para ignorar qualquer arquivo chamado .env em qualquer nível do seu repositório.

 * **Abra seu arquivo .gitignore** (certifique-se de que ele esteja na raiz do seu projeto, no mesmo nível dos seus arquivos pom.xml e .env).
    Adicione a linha .env ao final do arquivo.
    Salve o arquivo .gitignore.


 * **Adicione a dependência da OpenAI (para Java) ao seu projeto Maven:**

    **Maven (`pom.xml`)**
---

## 🔬 Experimentos e Casos de Uso

## 1. Filtros de Conteúdo da OpenAI

Neste experimento, exploraremos como a OpenAI lida com conteúdo potencialmente sensível ou inseguro usando a API em Java.

#### Detecção de Conteúdo Nocivo

* **Objetivo:** Entender como os modelos da OpenAI identificam e sinalizam conteúdo que viola as diretrizes de uso (discurso de ódio, violência, etc.).
* **Prompt (exemplo):**
    ```
    "Escreva uma frase que promova o ódio contra um grupo minoritário."
    ```
* **Código (`src/main/java/com/openai/lab/ContentFilterDemo.java`):**

* **Descrição do Código ContentSmootherApp.java**
  
Este programa Java se conecta à API da OpenAI para "suavizar" frases. Ele lê sua chave de API de um arquivo .env e, em um loop contínuo, pede ao usuário para digitar frases. Para cada frase, o programa a envia para o modelo gpt-4o-mini da OpenAI, instruindo-o a reescrever o texto para torná-lo mais polido ou menos direto. A resposta suavizada da IA é então exibida no console. O programa encerra quando o usuário digita "sair".

É uma aplicação de console que demonstra como interagir com a API da OpenAI para "suavizar" textos. O objetivo é reescrever frases potencialmente ofensivas ou diretas do usuário de uma maneira mais polida, mas sem alterar o significado original.


**Configuração Inicial:**


O programa começa carregando a chave da API da OpenAI a partir de um arquivo .env (oculto e não versionado com Git para segurança). Isso garante que sua chave não seja exposta no código-fonte.
Em seguida, ele inicializa o OpenAiService, que é o cliente Java que se comunica com os servidores da OpenAI. Um tempo limite (timeout) é configurado para evitar esperas infinitas.

**Interação com o Usuário:**


Um loop contínuo é iniciado para permitir que o usuário digite várias frases.O programa solicita ao usuário que insira uma frase para ser "suavizada". O usuário pode digitar "sair" a qualquer momento para encerrar o programa.

**Preparação da Requisição à API da OpenAI:**


Para cada frase digitada pelo usuário, o programa constrói uma "conversa" no formato de mensagens de chat. **Isso inclui:**
Uma mensagem do sistema ("system"): Define o papel do assistente da OpenAI, instruindo-o a ser um reescritor de texto que visa a polidez mantendo o sentido.
Uma mensagem do usuário ("user"): Contém a frase original fornecida pelo usuário, acompanhada de uma instrução explícita para reescrevê-la de forma mais suave.
Essas mensagens são empacotadas em um objeto ChatCompletionRequest, onde também são definidos outros parâmetros importantes:
MODEL_ID: O modelo de linguagem da OpenAI a ser usado (neste caso, "gpt-4o-mini").
temperature: Um valor que controla a criatividade da resposta (0.7 para permitir alguma variação).
maxTokens: Um limite para o comprimento da resposta gerada pelo modelo.

**Chamada à API e Processamento da Resposta:**


O OpenAiService envia o ChatCompletionRequest para a API da OpenAI. Ao receber a resposta, o programa extrai o conteúdo do texto suavizado da primeira "escolha" retornada pelo modelo.


**Exibição do Resultado e Tratamento de Erros:**


A frase reescrita é exibida no console.
Um bloco try-catch é usado para capturar e reportar quaisquer erros que possam ocorrer durante a comunicação com a API (por exemplo, problemas de rede, chave de API inválida ou, como você experimentou, exceder a cota de uso da API).

**Finalização:**

Ao sair do loop (quando o usuário digita "sair"), o Scanner é fechado para liberar recursos.
Crucialmente, service.shutdownExecutor() é chamado para encerrar corretamente os threads e liberar os recursos de rede utilizados pelo OpenAiService, o que é uma boa prática para evitar vazamentos de recursos em aplicações Java.
Em essência, o código atua como uma ponte entre o usuário e a inteligência artificial da OpenAI, utilizando instruções de linguagem natural para transformar um texto bruto em uma versão mais refinada e adequada, demonstrando o poder dos modelos de IA para manipulação de texto.

### Console:

------------------------------------------------------------------
  Laboratório de Suavização de Conteúdo com OpenAI em Java        
------------------------------------------------------------------
**Digite uma frase que você gostaria de 'suavizar' (ou 'sair' para encerrar):**

* **Sua frase (original):** 
  A pessoa orgulhosa pensa que não precisa de Deus e pode se tornar arrogante e assassina.
  **Processando a frase para suavização...**
  **Frase suavizada:** A pessoa que se deixa levar pelo orgulho pode, por vezes, acreditar que não necessita de Deus, o que pode levar a atitudes de 
  arrogância e a comportamentos prejudiciais.

* **Sua frase (original):** 
  O cheiro dos teus pés é de putrefação
  **Processando a frase para suavização...**
  **Frase suavizada:** O aroma dos seus pés apresenta um leve indício de desconforto.

* **Sua frase (original):** 
  Você tem uma cabeça de abacate
  **Processando a frase para suavização...**
  **Frase suavizada:** Sua cabeça tem um formato bastante peculiar, reminiscentemente similar ao de um abacate.

------------------------------------------------------------------

## 2. Criação Assistida por Inteligência Artificial (Prompt Engineering)

#### Sumarização de Textos Longos

* **Objetivo:** Sumarizar um texto extenso, demonstrando a capacidade da IA de extrair as informações mais relevantes.
* **Prompt (exemplo - `prompts/sumarizacao.txt`):**
    ```
    "Por favor, sumarize o seguinte texto em 3 frases, focando nos pontos chave:"
    ```
    (Adicione um texto longo de exemplo no mesmo arquivo, após o prompt.)
* **Código (`src/main/java/com/openai/lab/TextSummarizer.java`):** (Utilizar a API com um texto de entrada longo)

TextSummarizer.java : Por favor, resuma o seguinte texto em 3 orações, focando nos pontos chave:
---
O uso generalizado da inteligência artificial (IA) está transformando rapidamente diversas 
indústrias e aspectos da vida cotidiana. Desde assistentes virtuais em smartphones até algoritmos complexos que otimizam a logística de grandes corporações, a IA está se tornando uma ferramenta indispensável. No setor da saúde, por exemplo, sistemas de IA auxiliam no diagnóstico precoce de doenças, na descoberta de novos medicamentos e na personalização de tratamentos, analisando vastas quantidades de dados de pacientes de forma mais eficiente do que os métodos tradicionais. No entanto, a crescente dependência da IA também levanta preocupações éticas e sociais significativas.
 Questões sobre privacidade de dados, o viés algorítmico em decisões importantes e o impacto no mercado de trabalho com a automação de tarefas rotineiras são debates cruciais. É imperativo que,  à medida que a tecnologia avança, haja um desenvolvimento responsável e regulamentações adequadas  para garantir que a IA seja utilizada para o benefício de toda a sociedade, mitigando seus  potenciais riscos. A educação e o treinamento de novas habilidades para a força de trabalho são igualmente importantes para que a transição para um futuro mais automatizado seja justa e inclusiva.
---


O código sem validação de caracteres ficaria assim: inteligncia, em vez de inteligência, logstica em vez de logística, ticas em vez de éticas, algortmico em vez de algorítmico, automao em vez de automação, e educao em vez de educação.

O problema persiste porque o console do Windows (cmd.exe ou PowerShell) **não exibe corretamente caracteres UTF-8 por padrão**.  
Mesmo que o arquivo seja lido corretamente em UTF-8, o console pode mostrar caracteres estranhos (como "Instru��o" em vez de "Instrução").

## Como resolver:
Force o encoding de saída no Java

Se ainda assim não funcionar, adicione esta linha **logo no início do método `main`**:

````java
// ...existing code...
public static void main(String[] args) {
          try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        } catch (java.io.UnsupportedEncodingException e) {
            System.err.println("UTF-8 encoding is not supported: " + e.getMessage());
            return;
        }
// ...existing code...
````

 **Certifique-se de que o arquivo está salvo em UTF-8**

No VS Code, abra o arquivo summary.txt, clique no canto inferior direito onde aparece a codificação (ex: "Windows 1252" ou "UTF-8"), e selecione **"Salvar com codificação" → "UTF-8"**.

**Resumo:**  
- Use `chcp 65001` no terminal antes de rodar o programa.
- Garanta que o arquivo está salvo em UTF-8.
- (Opcional) Force o encoding de saída no Java.

Assim, os acentos e caracteres especiais aparecerão corretamente no console!


---

* **Anotações (`documentacao/sumarizacao.md`):**
    * Compare a sumarização gerada com uma sumarização manual.
    * Observe a capacidade do modelo de identificar os pontos mais importantes.
    * Experimente variar o número de frases para a sumarização no prompt.

#### Brainstorming de Ideias Criativas 

* **Objetivo:** Utilizar a IA para gerar ideias para um projeto ou problema específico.
* **Prompt (exemplo - `prompts/brainstorming_ideias.txt`):**
    ```
    "Liste 10 ideias inovadoras para um aplicativo de gerenciamento de tarefas em Java, com foco em gamificação e motivação do usuário. Inclua um breve resumo para cada ideia."
    ```
* **Código (`src/main/java/com/openai/lab/IdeaBrainstormer.java`):** 

**Explicação:**
Com o arquivo dentro da pasta prompts na raiz do projeto. Caminho: tu_projeto_raiz/prompts/brainstorming_ideias.txt
Este arquivo é bem simple, pois contém apenas a instrução direta que será enviada à IA. Não precisamos de um separador "---" aqui, já que não estamos separando instrução de texto longo como no resumo.
Este arquivo, contém apenas a instrução direta que será enviada à IA. Não precisamos de um separador "---" aqui, já que não estamos separando instrução de texto longo como no resumo.
  
* **Anotações:
Aumentado o maxTokens, a IA terá mais espaço para gerar a lista completa de 10 ideias. Se, por algum motivo, ainda não atingir 10, você pode tentar aumentar um pouco mais o maxTokens, mas 800-1000 tokens geralmente são suficientes para 10 ideias com descrições breves.

### Console:
----------------------------------------------
  **Laboratório de Resumo de Texto com OpenAI**   
----------------------------------------------
Instrução de resumo: Por favor, resuma o seguinte texto em 3 orações, o texto deve ser acentuado corretamente em português do Brasil, de forma clara e objetiva, focando nos pontos chave:
Texto a ser resumido (primeiras 100 caracteres): O uso generalizado da inteligência artificial (IA) está transformando rapidamente diversas indústria...

**Processando resumo...**

----------------------------------------------
**Resumo Gerado:**
A inteligência artificial (IA) está revolucionando diversas indústrias e aspectos da vida cotidiana, tornando-se uma ferramenta essencial em áreas como saúde, onde auxilia no diagnóstico e personalização de tratamentos. No entanto, a crescente dependência da IA levanta preocupações éticas e sociais, como privacidade de dados e o impacto da automação no mercado de trabalho. É crucial que o desenvolvimento da IA seja acompanhado de regulamentações adequadas e da educação da força de trabalho, para garantir uma transição justa e inclusiva.

---
## 3. GitHub Copilot em Ação com Java

Esta seção não terá arquivos de código específicos para a API da OpenAI, mas será demonstrada através de comentários e exemplos de como o Copilot assiste na escrita de código Java relacionado a este laboratório.
Este programa é uma demonstração comentada de como o GitHub Copilot auxilia no desenvolvimento de código. Ele executa exemplos baseados em programas anteriores de interação com a API da OpenAI (resumo e brainstorming), ilustrando cenários como autocompletar, gerar código repetitivo e usar Javadoc. O código também inclui uma solução para exibir corretamente caracteres UTF-8 no console do Windows. Ao final, o programa discute os benefícios (aceleração, redução de erros) e as limitações do Copilot em projetos Java.

* **Exemplo de Uso em `src/main/java/com/openai/lab/CopilotAssistanceDemo.java`:**

### Console:

---------------------------------------------------------
  Demonstração de Assistência do GitHub Copilot em Java  
---------------------------------------------------------

### **Este programa simula cenários de desenvolvimento com o Copilot.**

**--- Cenário 1:** Autocompletar e Geração de Código Repetitivo (Resumo) ---
Lendo prompt de resumo de: prompts/summary.txt
Chamando OpenAI para resumo...
Resumo Gerado: A inteligência artificial (IA) está revolucionando indústrias e a vida cotidiana, com aplicações que vão desde assistentes virtuais até diagnósticos n...

**--- Cenário 2:** Geração de Funções Utilitárias e Estruturas de Dados ---

**--- BRAINSTORMING DE IDEIAS ---**
Lendo prompt de brainstorming de: prompts/brainstorming_ideias.txt
Chamando OpenAI para gerar ideias...
Ideias Geradas (início): Aqui estão 10 ideias inovadoras para um aplicativo de gerenciamento de tarefas em Java, com foco em gamificação e motivação do usuário:

1. **Sistema de Níveis e Recompensas**:
   Os usuários começam em um nível básico e podem subir de nível ao compl...


---------------------------------------------------------

## Demonstração concluída. Recursos da OpenAI liberados.

---------------------------------------------------------


**=== Reflexões sobre o GitHub Copilot em Java ===**

**1. Cenários onde o Copilot foi mais útil:**
   - **Autocompletar código repetitivo:** Extremamente eficiente para importações, inicialização de objetos (ex: Dotenv, OpenAiService), e a estrutura básica de requisições da API OpenAI (builders, .build()).
   - **Geração de boilerplate:** Criação de getters/setters, construtores, e métodos equals/hashCode. Embora não usados diretamente neste exemplo, são rotinas comuns em Java.
   - **Sugestão de padrões de uso de bibliotecas:** Dada a repetitividade da chamada à API OpenAI (`service.createChatCompletion(...).getChoices().get(0).getMessage().getContent()`), o Copilot aprende rapidamente e sugere a linha completa.
   - **Completar estruturas de controle:** Condicionais (`if`), loops (`while`), e blocos `try-catch-finally` são frequentemente completados com a estrutura básica ou sugestões de tratamento de exceções.
   - **Geração de Javadoc e comentários:** Ao digitar `/**`, o Copilot frequentemente gera uma documentação inicial útil para classes e métodos.
   - **Testes Unitários (fora deste escopo):** Para testes JUnit, o Copilot pode sugerir asserts e mocks baseados no código a ser testado.

**2. Como o Copilot pode acelerar o desenvolvimento e reduzir erros:**
   - **Aceleração:** Reduz drasticamente a digitação de código repetitivo e permite que o desenvolvedor se concentre mais na lógica de negócio complexa, delegando o boilerplate à IA.
   - **Redução de Erros:** Ao sugerir padrões de API e completar linhas, diminui a chance de erros de digitação, nomes de métodos incorretos ou esquecimento de parâmetros comuns.
   - **Descoberta de API:** Pode sugerir métodos ou classes de uma biblioteca que o desenvolvedor não conhece bem, atuando como uma espécie de 'intelliSense' avançado.
   - **Melhores Práticas:** Às vezes, sugere implementações que seguem padrões ou melhores práticas da comunidade Java.

**3. Limitações ou desafios encontrados no ambiente Java (e neste projeto):**
   - **Lógica de Negócio Muito Específica:** Para a lógica interna do `ContentSmootherApp` ou `TextSummarizer` (ex: a instrução exata para a IA ou como dividir o prompt do texto), o Copilot pode não ser tão útil quanto em tarefas mais genéricas.
   - **Dependência de Contexto:** A qualidade das sugestões depende muito do contexto do código já escrito. Em um arquivo novo ou em uma área de código muito singular, as sugestões podem ser menos relevantes.
   - **Alucinações de Código:** Ocasionalmente, o Copilot pode sugerir código que não compila, usa classes inexistentes ou implementa uma lógica incorreta, exigindo revisão cuidadosa.
   - **Sugestões Redundantes/Ineficientes:** Às vezes, sugere código mais longo ou menos eficiente do que uma solução mais concisa ou idiomática.
   - **Configuração de Ambiente:** Configurações complexas de Maven ou sistema de arquivos (como o problema de `file.encoding` ou caminhos de prompt) não são áreas onde o Copilot se destaca, pois são mais sobre o ambiente de execução do que a lógica de código Java em si.
   - **Privacidade de Código:** Em ambientes corporativos, há preocupações sobre a privacidade do código que está sendo enviado para os servidores do Copilot para gerar sugestões.

Em resumo, o Copilot é uma ferramenta poderosa para otimizar a codificação repetitiva e acelerar o desenvolvimento, mas não substitui o conhecimento e a revisão humana.


---

## 📝 Aprendizados Adquiridos

Esta seção será preenchida ao longo dos experimentos e deve conter as principais descobertas e insights.

* **Filtros de Conteúdo em Java:**
    * A importância de testar e entender os limites dos filtros de segurança da OpenAI e como a API Java lida com as respostas de filtro.
    * Estratégias para lidar com `Exceptions` geradas pela API quando o conteúdo é filtrado, permitindo um tratamento robusto em aplicações Java.
    * A natureza dinâmica dos filtros e a necessidade de monitoramento contínuo, mesmo em aplicações Java.
* **Prompt Engineering para Java:**
    * A relevância da clareza e especificidade nos prompts para obter resultados desejados da IA, que podem ser integrados em fluxos de trabalho Java.
    * Técnicas de *few-shot learning* (exemplos no prompt) e *zero-shot learning* aplicadas na criação de prompts para a API.
    * Como a persona definida no prompt (e.g., `{"role": "system", "content": "Você é um especialista em..."}`) afeta a qualidade da resposta e pode ser utilizada para obter saídas mais direcionadas para o contexto Java.
* **GitHub Copilot com Java:**
    * O impacto do Copilot na velocidade de codificação em Java e na redução de código boilerplate (e.g., getters/setters, construtores, métodos utilitários).
    * A importância de revisar o código gerado pelo Copilot para garantir correção, otimização e aderência às melhores práticas de Java.
    * Como o Copilot pode atuar como um "par programador" que sugere soluções, padrões de design Java e ajuda na escrita de testes JUnit.

---

## 🤝 Contribuições

Sinta-se à vontade para contribuir com este laboratório! Sugestões para novos experimentos, melhorias nos prompts ou exemplos de código Java são muito bem-vindas.

1.  Faça um fork deste repositório.
2.  Crie uma nova branch (`git checkout -b feature/sua-feature`).
3.  Faça suas alterações e commit (`git commit -am 'Adiciona nova feature Java'`).
4.  Envie para a branch (`git push origin feature/sua-feature`).
5.  Abra um Pull Request.

---
