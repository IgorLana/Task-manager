package com.tarefas.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Task extends BaseItem{

    private String title;
    private Pdca pdca;

    private List<ChecklistItem> checklist = new ArrayList<>();

    public Task(String id, Priority priority, String descricao, LocalDate dueDate, String title, Pdca pdca, List<ChecklistItem> checklist, CardType cardType ) {
        super(id, priority, descricao, dueDate, cardType);
        this.title = title;
        this.pdca = pdca;
        this.checklist = checklist;
    }

}
