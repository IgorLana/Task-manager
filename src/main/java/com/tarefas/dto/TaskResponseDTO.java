package com.tarefas.dto;

import com.tarefas.model.ChecklistItem;
import com.tarefas.model.Pdca;
import com.tarefas.model.Priority;

import java.time.LocalDate;
import java.util.List;

public record TaskResponseDTO(
        String id,
        String descricao,
        Priority priority,
        String title,
        Pdca pdca,
        List<ChecklistItem> checklist,
        LocalDate dueDate
) {
}
