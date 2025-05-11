package com.tarefas.model;

public enum Status {
    PENDENTE("Pendente"),
    CONCLUIDA("Concuída"),
    ADIADA("Adiado"),
    EM_ANDAMENTO("Em andamento");


    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}


