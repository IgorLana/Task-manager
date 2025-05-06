package com.tarefas.controller;
import com.tarefas.model.Priority;
import com.tarefas.model.Status;
import com.tarefas.model.Tarefa;
import com.tarefas.service.TarefaService;

import java.time.LocalDate;
import java.util.List;

public class TarefaController {

    private final TarefaService service = new TarefaService();
    public  void adicionarTarefa(String descricao, LocalDate dueDate, Priority priority) {
        service.adicionar(descricao, dueDate, priority);
    }
    public  void listarTarefas() {
        service.listarTarefas();
    }
    public boolean alterarStatus(int num, Status novoStatus) {return service.alterarStatus(num, novoStatus);}
    public boolean alterarPrioridade(int num, Priority newPriority) {return service.alterarPrioridade(num, newPriority);}
    public boolean alterarDescricao(int num, String novaDescricao) {return service.alterarDescricao(num, novaDescricao);}
    public List<Tarefa> listarPorStatus(Status s) {service.listarPorStatus(s);
        return null;
    }
    public List<Tarefa> listarPorPrioridade(Priority p) {service.listarPorPrioridade(p);
    return null;}
    public void salvar() { service.salvar(); }



}
