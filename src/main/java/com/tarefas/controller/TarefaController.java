package com.tarefas.controller;

import com.tarefas.entity.UsuarioEntity;
import com.tarefas.model.*;

import com.tarefas.service.TarefaService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;


@Component
public class TarefaController {

    private final TarefaService service;

    public TarefaController(TarefaService service) {
        this.service = service;      // injeção via construtor
    }



    public void adicionarTarefa(String desc,
                                Priority prio,
                                LocalDate due,
                                UsuarioEntity usuario) {
        service.adicionar(desc, prio, due, usuario);
    }

    public List<ToDo> listarTarefas() {
        return service.listar();
    }

    public boolean alterarStatus(String id, Status novo) {
        return service.alterarStatus(id, novo);
    }

    public boolean alterarPrioridade(String id, Priority nova) {
        return service.alterarPrioridade(id, nova);
    }

    public boolean editarDescricao(String id, String novaDesc) {
        return service.editarDescricao(id, novaDesc);
    }

    public boolean remover(String id) {
        return service.remover(id);
    }


    public List<ToDo> listarPorStatus(Status s)      { return service.listarPorStatus(s); }
    public List<ToDo> listarPorPriority(Priority p)  { return service.listarPorPriority(p); }
}