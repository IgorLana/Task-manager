package com.tarefas.controller;
import com.tarefas.service.TarefaService;

public class TarefaController {

    private final TarefaService service = new TarefaService();
    public  void adicionarTarefa(String descricao) {
        service.adicionar(descricao);
    }
    public  void listarTarefas() {
        service.listarTarefas();
    }
    public  void concluirTarefa(int num) {
        service.concluirTarefa(num);
    }
    public void salvar() { service.salvar(); }



}
