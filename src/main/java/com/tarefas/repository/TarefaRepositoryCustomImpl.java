package com.tarefas.repository;

import com.tarefas.entity.TarefaEntity;
import com.tarefas.entity.UsuarioEntity;
import com.tarefas.model.Priority;
import com.tarefas.model.Status;
import com.tarefas.model.ToDo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TarefaRepositoryCustomImpl implements TarefaRepositoryCustom {

    private final UsuarioRepository usuarioRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public TarefaRepositoryCustomImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<ToDo> findAllByUsuarioLogado() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioEntity usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        String jpql = "SELECT t FROM TarefaEntity t WHERE t.usuario.id = :idUsuario";
        return entityManager.createQuery(jpql, TarefaEntity.class)
                .setParameter("idUsuario", usuario.getId())
                .getResultList()
                .stream()
                .map(entity -> new ToDo(
                        entity.getId(),
                        entity.getDescricao(),
                        entity.getStatus(),
                        entity.getDueDate(),
                        entity.getPriority()
                ))
                .toList();
    }

    @Override
    public List<ToDo> findAllByStatusAndUsuarioLogado(Status s) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioEntity usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        String jpql = "SELECT t FROM TarefaEntity t WHERE t.usuario.id = :idUsuario AND t.status = :status";
        return entityManager.createQuery(jpql, TarefaEntity.class)
                .setParameter("idUsuario", usuario.getId())
                .setParameter("status", s)
                .getResultList()
                .stream()
                .map(entity -> new ToDo(
                        entity.getId(),
                        entity.getDescricao(),
                        entity.getStatus(),
                        entity.getDueDate(),
                        entity.getPriority()
                ))
                .toList();

    }

    @Override
    public List<ToDo> findAllByPriorityAndUsuarioLogado(Priority p) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioEntity usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        String jpql = "SELECT t FROM TarefaEntity t WHERE t.usuario.id = :idUsuario AND t.priority = :priority";
        return entityManager.createQuery(jpql, TarefaEntity.class)
                .setParameter("idUsuario", usuario.getId())
                .setParameter("priority", p)
                .getResultList()
                .stream()
                .map(entity -> new ToDo(
                        entity.getId(),
                        entity.getDescricao(),
                        entity.getStatus(),
                        entity.getDueDate(),
                        entity.getPriority()
                ))
                .toList();

    }



}

