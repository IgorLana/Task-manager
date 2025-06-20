package com.tarefas.model;



public enum PDCA {
    PLAN("Planejar"),
    DO("Fazer"),
    CHECK("Verificar"),
    ACT("Agir");


    private final String PDCA;

    PDCA(String PDCA) {
        this.PDCA = PDCA;
    }

    public String getStatus() {
        return PDCA;
    }
}