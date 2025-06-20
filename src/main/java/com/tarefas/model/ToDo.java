package com.tarefas.model;

import java.time.LocalDate;

public class ToDo {

    private Long id;
    private String descricao;
    private Status status;
    private Priority priority;
    private LocalDate dueDate;

    @Override
    public String toString() {
        return "Tarefa: " + descricao + " || Status: [" + status.getStatus() + "]" + " || Prioridade: " + priority.getPriority() + " || Vence em: " + dueDate + "\n";
    }



    public ToDo(Long id, String descricao, Status status, LocalDate dueDate, Priority priority) {
        this.id = id;
        this.descricao = descricao;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public Long getId() { return id; }
    public LocalDate getDueDate() { return dueDate; }


    public Status getStatus()       { return status; }
    public String getDescricao()    { return descricao; }




    public Priority getPriority() {
    return priority;}

    public void setPriority(Priority newPriority) {
        this.priority = newPriority;
    }

    public void setDescricao(String novaDesc) {
        this.descricao = novaDesc;
    }

    public void setStatus(Status novo) {
        this.status = novo;
    }
}
