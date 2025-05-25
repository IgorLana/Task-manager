package com.tarefas.service;

import com.tarefas.entity.TarefaEntity;
import com.tarefas.mapper.TarefaMapper;
import com.tarefas.model.Priority;
import com.tarefas.model.Tarefa;
import com.tarefas.repository.JpaTarefaRepository;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.tarefas.model.Status;

import java.time.LocalDate;
import java.util.*;


@Service
@Transactional
public class TarefaService {

    private final JpaTarefaRepository repo;

    public TarefaService(JpaTarefaRepository repo) {
        this.repo = repo;

    }

    public void adicionar(String desc, Priority prio, LocalDate dueDate) {
        repo.save(new TarefaEntity(null, desc, Status.PENDENTE, prio, dueDate));
    }

    private Tarefa toModel(TarefaEntity e) {
        return new Tarefa(null, e.getDescricao(),e.getStatus(), e.getDueDate(), e.getPriority());

    }

    public List<Tarefa> listar() {
        return repo.findAll().stream()
                .map(TarefaMapper::toModel)   // seu mapper
                .toList();
    }
    public boolean alterarStatus(Long id, Status novo) {
        return repo.findById(id).map(ent -> {
            ent.setStatus(novo);
            return true;
        }).orElse(false);
    }

    public List<Tarefa> listarPorStatus(Status s) {
        return repo.findByStatus(s).stream()
                .map(this::toModel)
                .toList();
    }

    public List<Tarefa> listarPorPriority(Priority p) {
        return repo.findByPriority(p).stream()
                .map(this::toModel)
                .toList();
    }

    public boolean alterarPrioridade(long id, Priority newPriority) {
        return repo.findById(id)
                .map(ent -> { ent.setPriority(newPriority); return true; })
                .orElse(false);
    }
    public boolean editarDescricao(long id, String novaDesc) {
        return repo.findById(id)
                .map(ent -> { ent.setDescricao(novaDesc); return true; })
                .orElse(false);
    }

    public boolean remover(long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

}