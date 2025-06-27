package com.tarefas.repository;

import com.tarefas.entity.TaskEntity;
import com.tarefas.entity.UsuarioEntity;
import com.tarefas.model.Pdca;
import com.tarefas.model.Priority;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository
        extends MongoRepository<TaskEntity, String> {

    List<TaskEntity> findByUser(UsuarioEntity user);
    List<TaskEntity> findByPdcaAndUser(Pdca pdca, UsuarioEntity user);
    List<TaskEntity> findByPriorityAndUser(Priority priority, UsuarioEntity user);


}
