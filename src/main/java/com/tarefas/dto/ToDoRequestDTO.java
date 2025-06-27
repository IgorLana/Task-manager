package com.tarefas.dto;

import com.tarefas.model.Priority;
import com.tarefas.model.Status;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ToDoRequestDTO(

    @NotBlank(message = "Descrição é obrigatória")
    String descricao,

    @NotNull(message = "Prioridade é obrigatória")
    Priority priority,

    @NotNull(message = "Data de vencimento é obrigatorio")
    @Future(message = "Data deve estar no futuro")
    LocalDate dueDate,

    Status status

){}
