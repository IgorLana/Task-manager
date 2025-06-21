package com.tarefas.repository;

import com.tarefas.entity.TarefaEntity;
import com.tarefas.entity.UsuarioEntity;
import com.tarefas.model.Priority;
import com.tarefas.model.Status;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TarefaRepository
    extends MongoRepository<TarefaEntity, String>{

    List<TarefaEntity> findByStatus(Status status);
    List<TarefaEntity> findByPriority(Priority priority);

    List<TarefaEntity> findByUser(UsuarioEntity user);

    // Lista tarefas por Status e para um determinado usuário
    List<TarefaEntity> findByStatusAndUser(Status status, UsuarioEntity user);

    // Lista tarefas por Priority e para um determinado usuário
    List<TarefaEntity> findByPriorityAndUser(Priority priority, UsuarioEntity user);

}



