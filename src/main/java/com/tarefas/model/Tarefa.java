package com.tarefas.model;

public class Tarefa {

    private String descricao;
    private Status status;


    public Tarefa(String descricao) {
        this.descricao = descricao;
        this.status = Status.PENDENTE;
    }

    public void marcarConcluida() {
        this.status = Status.CONCLUIDA;
    }
    public Status getStatus()       { return status; }
    public String getDescricao()    { return descricao; }

    @Override
    public String toString() {
        return "Tarefa: " + descricao + " [" + status.getRotulo() + "]";
    }

}
