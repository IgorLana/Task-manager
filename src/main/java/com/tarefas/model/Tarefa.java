package com.tarefas.model;

import java.time.LocalDate;

public class Tarefa {

    private String descricao;
    private Status status;
    private Priority priority;
    private LocalDate dueDate;


    public Tarefa(String descricao, LocalDate dueDate, Priority priority) {
        this.descricao = descricao;
        this.status = Status.PENDENTE;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate d){this.dueDate = d;}

    public Priority getPrioridade() { return priority; }
    public Status getStatus()       { return status; }
    public String getDescricao()    { return descricao; }
    public void setStatus(Status novoStatus) { this.status = novoStatus; }
    public void setPriority(Priority newPriority) { this.priority = newPriority; }
    public void setDescricao(String novaDescricao) { this.descricao = novaDescricao; }



    @Override
    public String toString() {
        return "Tarefa: " + descricao + " || Status: [" + status.getRotulo() + "]" + " || Prioridade: " + priority.getPriority() + " || Vence em: " + dueDate + "\n";
    }

}
