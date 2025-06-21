package com.tarefas.entity;
import com.tarefas.model.Priority;
import com.tarefas.model.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDate;

@Document(collection = "task")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TarefaEntity {

    @Id
    private String  id;

    private String descricao;


    private Status status;

    private Priority priority;

    private LocalDate dueDate;

    @DBRef
    private UsuarioEntity user;


    public TarefaEntity(String id, String descricao, Status status,
                        Priority priority, LocalDate dueDate) {

        this.id = id;
        this.descricao = descricao;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;

    }

}


