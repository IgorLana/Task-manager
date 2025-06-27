package com.tarefas.entity;

import com.tarefas.model.CardType;
import com.tarefas.model.ChecklistItem;
import com.tarefas.model.Pdca;
import com.tarefas.model.Priority;
import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "task")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {

    @Id
    private String id;
    private Priority priority;
    private String descricao;
    private LocalDate dueDate;
    private String title;
    private Pdca pdca;
    private List<ChecklistItem> checklist = new ArrayList<>();
    private CardType cardType;

    @DBRef
    private UsuarioEntity user;

    public TaskEntity(String id, Priority priority, String descricao, LocalDate dueDate, String title, Pdca pdca, List<ChecklistItem> checklist, CardType cardType) {
        this.id = id;
        this.priority = priority;
        this.descricao = descricao;
        this.dueDate = dueDate;
        this.title = title;
        this.pdca = pdca;
        this.checklist = checklist;
        this.cardType = cardType;

    }



}
