package com.tarefas.dto;

import com.tarefas.model.ChecklistItem;
import com.tarefas.model.Pdca;
import com.tarefas.model.Priority;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;


public record TaskRequestDTO(


    @NotBlank(message = "Descrição é obrigatória")
    String descricao,

    @NotNull(message = "Prioridade é obrigatória")
    Priority priority,

    @NotBlank(message = "Titulo é obrigatória")
    String title,

    @NotNull(message = "PDCA é obrigatória")
    Pdca pdca,

    List<ChecklistItem> checklist,

    @NotNull(message = "Data de vencimento é obrigatorio")
    @Future(message = "Data deve estar no futuro")
    LocalDate dueDate
)
{}