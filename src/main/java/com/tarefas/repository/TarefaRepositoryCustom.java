package com.tarefas.repository;

import com.tarefas.model.Priority;
import com.tarefas.model.Status;
import com.tarefas.model.ToDo;

import java.util.List;

public interface TarefaRepositoryCustom {
     List<ToDo> findAllByUsuarioLogado();
     List<ToDo> findAllByStatusAndUsuarioLogado(Status s);
     List<ToDo> findAllByPriorityAndUsuarioLogado(Priority p);
}