package com.tarefas.repository;

import com.tarefas.entity.ToDoEntity;
import com.tarefas.entity.UsuarioEntity;
import com.tarefas.model.Priority;
import com.tarefas.model.Status;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ToDoRepository
    extends MongoRepository<ToDoEntity, String>{

    List<ToDoEntity> findByUser(UsuarioEntity user);

    // Lista tarefas por Status e para um determinado usuário
    List<ToDoEntity> findByStatusAndUser(Status status, UsuarioEntity user);

    // Lista tarefas por Priority e para um determinado usuário
    List<ToDoEntity> findByPriorityAndUser(Priority priority, UsuarioEntity user);

}



