package com.tarefas.mapper;

import com.tarefas.dto.TarefaRequestDTO;
import com.tarefas.dto.TarefaResponseDTO;
import com.tarefas.entity.TarefaEntity;

public class TarefaDtoMapper {

    public static TarefaEntity toEntity(TarefaRequestDTO dto) {
        return new TarefaEntity(null, dto.descricao(),null, dto.priority(), dto.dueDate());
    }

    public static TarefaResponseDTO toResponseDTO(TarefaEntity e){
        return new TarefaResponseDTO(e.getId(), e.getDescricao(), e.getStatus(), e.getPriority(), e.getDueDate());

    }

}
