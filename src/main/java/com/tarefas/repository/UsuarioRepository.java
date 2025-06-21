package com.tarefas.repository;

import com.tarefas.entity.UsuarioEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByUsername(String username);

}
