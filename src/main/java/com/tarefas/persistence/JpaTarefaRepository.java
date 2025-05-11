package com.tarefas.persistence;

import com.tarefas.model.Priority;
import com.tarefas.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaTarefaRepository
    extends JpaRepository<TarefaEntity, Long>{

    List<TarefaEntity> findByStatus(Status status);
    List<TarefaEntity> findByPriority(Priority priority);

}



