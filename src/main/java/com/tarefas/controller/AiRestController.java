package com.tarefas.controller;


import com.tarefas.model.Task;
import com.tarefas.service.TaskGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class AiRestController {
    private final TaskGenerationService taskGenerationService;

    @Autowired
    public AiRestController(TaskGenerationService taskGenerationService) {
        this.taskGenerationService = taskGenerationService;
    }

    @PostMapping("/generate")
    public ResponseEntity<Task> generateTask(@RequestBody String userInput) {
        // 1º Usuário descreve a necessidade dele (o userInput já é recebido aqui)
        // 2º e 3º A lógica de chamar a API Gemini será feita no serviço
        Task generatedTask = taskGenerationService.generateTaskFromJson(userInput);

        // 5º Enviamos esse JSON para o front-end do usuário.
        return ResponseEntity.ok(generatedTask);
    }
}
