package com.tarefas.model;

public enum CardType {
    TODO("To-Do"),
    TASK("Tarefa");

    private final String CardType;

    CardType(String CardType) {this.CardType = CardType;}
}
