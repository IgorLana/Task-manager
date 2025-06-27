package com.tarefas.model;



public enum Pdca {
    PLAN("Planejar"),
    DO("Fazer"),
    CHECK("Verificar"),
    ACT("Agir");


    private final String Pdca;

    Pdca(String PDCA) {
        this.Pdca = PDCA;
    }

}