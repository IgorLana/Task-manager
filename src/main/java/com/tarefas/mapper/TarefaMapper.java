package com.tarefas.mapper;

import com.tarefas.model.Tarefa;

import com.tarefas.entity.TarefaEntity;

public final class TarefaMapper {
    private TarefaMapper() {}

    // Entity → Model
    public static Tarefa toModel(TarefaEntity e) {
        return new Tarefa(
                e.getId(),// ajuste ao seu construtor
                e.getDescricao(),
                e.getStatus(),
                e.getDueDate(),
                e.getPriority());
    }

    // Model → Entity
    public static TarefaEntity toEntity(Tarefa m) {
        return new TarefaEntity(
                m.getId(),
                m.getDescricao(),
                m.getStatus(),
                m.getPriority(),
                m.getDueDate());
    }
}