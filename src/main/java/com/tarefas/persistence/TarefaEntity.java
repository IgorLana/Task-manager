package com.tarefas.persistence;
import com.tarefas.model.Priority;
import com.tarefas.model.Status;
import jakarta.persistence.*;


import java.time.LocalDate;

@Entity
@Table(name = "tarefas")
public class TarefaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    private LocalDate dueDate;

    public TarefaEntity() {}

    public TarefaEntity(Long id, String descricao, Status status,
                        Priority priority, LocalDate dueDate) {

        this.id = id;
        this.descricao = descricao;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;

    }

    public Long getId() { return id; }
    public String getDescricao(){return descricao;}
    public void setDescricao(String novaDescricao){this.descricao = novaDescricao;}

    public Status getStatus(){return status;}
    public void setStatus(Status novoStatus){ this.status = novoStatus;}

    public Priority getPriority(){return priority;}
    public void setPriority(Priority newPriority){this.priority = newPriority;}

    public LocalDate getDueDate(){return dueDate;}
    public void setDueDate(LocalDate newDueDate){this.dueDate = newDueDate;}


}


