package com.tarefas.model;

public enum Status {
    PENDENTE("Pendente"),
    CONCLUIDA("Concuída"),
    ADIADA("Adiado"),
    EM_ANDAMENTO("Em andamento");


    private final String rotulo;

    Status(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getRotulo() {
        return rotulo;
    }
}


