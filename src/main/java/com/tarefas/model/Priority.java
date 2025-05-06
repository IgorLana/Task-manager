package com.tarefas.model;

public enum Priority{
    LOW("Baixo"),
    MEDIUM("Médio"),
    HIGH("Alto");

    private final String priority;

    Priority(String priority) {this.priority = priority;}
    public String getPriority() {return priority;}

}