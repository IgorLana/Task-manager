package com.tarefas.dto;

import com.tarefas.model.Priority;
import com.tarefas.model.Status;

import java.time.LocalDate;

public record TarefaResponseDTO(
        Long id,
        String descricao,
        Status status,
        Priority prioridade,
        LocalDate dueDate
) {
}
