package com.tarefas.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.tarefas.model.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class TaskGenerationService {

    @Value("${google.genai.api-key}") // Injeta a chave da API do application.properties
    private String apiKey;

    public Task generateTaskFromJson(String userInput) {

        String fullPrompt;
        fullPrompt = """
                You are an expert task manager and planner. Your goal is to convert a user's free-form request into a structured JSON object representing a task.
                The user will provide a description of their need. Based on this description, you must generate a comprehensive task in the specified JSON format.

                The JSON format must strictly adhere to the following structure:
                ```json
                {
                  "priority": "ENUM_PRIORITY", // Can be "LOW", "MEDIUM", "HIGH"
                  "descricao": "Detailed description of the task based on user input."
                  "dueDate": "YYYY-MM-DD", // Estimated completion date. MUST be a valid future date.
                  "title": "Concise title for the task."
                  "pdca": "ENUM_PDCA", // Can be "PLAN", "DO", "CHECK", "ACT"
                  "checklist": [
                    {
                      "label": "Specific sub-task or step.",
                      "checked": false // Default to false
                    },
                    {
                      "label": "Another specific sub-task or step.",
                      "checked": false
                    }
                  ]
                }
                ```

                **Guidelines for generation:**
                -   **priority**: Based on the urgency implied in the user's request. Default to "MEDIUM" if not specified.
                -   **descricao**: Elaborate on the user's request, adding clarity and detail.
                -   **dueDate**: Estimate a realistic completion date. If the user mentions a specific timeframe (e.g., "by next week"), try to incorporate it. Otherwise, assume a reasonable future date (e.g., 7-14 days from today's date). Today's date is %s.
                -   **title**: A short, descriptive title.
                -   **pdca**: Infer the most appropriate PDCA phase. Default to "DO" if unclear.
                -   **checklist**: Break down the task into 3-5 actionable and distinct sub-tasks. Each sub-task should have a `label` and `checked` (default `false`).

                **Example User Input:**
                "Preciso organizar uma reunião com a equipe de vendas para discutir as metas do próximo trimestre."

                **Example JSON Output (for the above input):**
                ```json
                {
                  "priority": "MEDIUM",
                  "descricao": "Organizar e conduzir uma reunião com toda a equipe de vendas para discutir e alinhar as metas para o próximo trimestre, garantindo que todos compreendam os objetivos e suas responsabilidades.",
                  "dueDate": "2025-07-05",
                  "title": "Reunião de Metas Q3 Vendas",
                  "pdca": "PLAN",
                  "checklist": [
                    {
                      "label": "Definir pauta da reunião e objetivos.",
                      "checked": false
                    },
                    {
                      "label": "Convidar equipe de vendas e agendar sala/plataforma.",
                      "checked": false
                    },
                    {
                      "label": "Preparar materiais de apresentação (metas, resultados Q2).",
                      "checked": false
                    },
                    {
                      "label": "Enviar convite e agenda para os participantes.",
                      "checked": false
                    }
                  ]
                }
                ```

                ---
                Now, generate the JSON for the following user input:

                User Input: "%s"
                """.formatted(LocalDate.now().format(DateTimeFormatter.ISO_DATE), userInput);
        System.out.println(fullPrompt);

        GenerateContentResponse response = GenerateTextFromTextInput(fullPrompt);
        String generatedJsonString = response.text();
        System.out.println("JSON recebido do Gemini (bruto): " + generatedJsonString); // Para depuração

        // --- Adição para remover os delimitadores de bloco de código Markdown ---
        if (generatedJsonString.startsWith("```json")) {
            generatedJsonString = generatedJsonString.substring("```json".length()).trim();
        }
        if (generatedJsonString.endsWith("```")) {
            generatedJsonString = generatedJsonString.substring(0, generatedJsonString.length() - "```".length()).trim();
        }
        // --- Fim da adição ---

        System.out.println("JSON após remoção dos delimitadores: " + generatedJsonString); // Para depuração

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            Task generatedTask = objectMapper.readValue(generatedJsonString, Task.class);
            return generatedTask;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }



    public GenerateContentResponse GenerateTextFromTextInput(String prompt) {
        // Use o builder para construir o Client, passando a apiKey injetada
        Client client = Client.builder().apiKey(apiKey).build();

        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-2.5-flash",
                        prompt,
                        null);

        System.out.println(response.text());
        return response;
    }
}
