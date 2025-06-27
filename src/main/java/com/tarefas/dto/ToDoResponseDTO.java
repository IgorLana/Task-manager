package com.tarefas.dto;

import com.tarefas.model.Priority;
import com.tarefas.model.Status;

import java.time.LocalDate;

public record ToDoResponseDTO(
        String  id,
        String descricao,
        Status status,
        Priority prioridade,
        LocalDate dueDate
) {
}
