package com.tarefas.controller;

import com.tarefas.model.*;

import com.tarefas.service.TarefaService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * Camada de orquestração – recebe pedidos da View
 * e encaminha ao TarefaService.
 */
@Component          // vira bean Spring para injeção na View/CLI ou REST
public class TarefaController {

    private final TarefaService service;

    public TarefaController(TarefaService service) {
        this.service = service;      // injeção via construtor
    }

    /* ---------- Ações de uso pela View CLI ---------- */

    public void adicionarTarefa(String desc,
                                Priority prio,
                                LocalDate due) {
        service.adicionar(desc, prio, due);
    }

    public List<Tarefa> listarTarefas() {
        return service.listar();
    }

    public boolean alterarStatus(long id, Status novo) {
        return service.alterarStatus(id, novo);
    }

    public boolean alterarPrioridade(long id, Priority nova) {
        return service.alterarPrioridade(id, nova);
    }

    public boolean editarDescricao(long id, String novaDesc) {
        return service.editarDescricao(id, novaDesc);
    }

    public boolean remover(long id) {
        return service.remover(id);
    }

    /* ---------- Filtros (se a View precisar) ---------- */

    public List<Tarefa> listarPorStatus(Status s)      { return service.listarPorStatus(s); }
    public List<Tarefa> listarPorPriority(Priority p)  { return service.listarPorPriority(p); }
}