package com.tarefas.service;

import com.tarefas.entity.TarefaEntity;
import com.tarefas.entity.UsuarioEntity;
import com.tarefas.model.Priority;
import com.tarefas.model.ToDo;
import com.tarefas.repository.JpaTarefaRepository;
import com.tarefas.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tarefas.model.Status;

import java.time.LocalDate;
import java.util.*;


@Service
@Transactional
public class TarefaService {

    private final JpaTarefaRepository repo;
    private final UsuarioRepository usuarioRepository;
    public TarefaService(JpaTarefaRepository repo, UsuarioRepository usuarioRepository) {
        this.repo = repo;
        this.usuarioRepository = usuarioRepository;


    }

    public void adicionar(String desc, Priority prio, LocalDate dueDate, UsuarioEntity usuario) {
        TarefaEntity entity = new TarefaEntity(null, desc, Status.PENDENTE, prio, dueDate);
        entity.setUsuario(usuario); // associa a tarefa ao usuário logado
        repo.save(entity);
    }

    private ToDo toModel(TarefaEntity e) {
        return new ToDo(null, e.getDescricao(),e.getStatus(), e.getDueDate(), e.getPriority());

    }

    public List<ToDo> listar() {
        return repo.findAllByUsuarioLogado();
    }
    public boolean alterarStatus(Long id, Status novo) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioEntity usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return repo.findById(id).map(ent -> {
            if (!ent.getUsuario().getId().equals(usuario.getId())) {
                throw new AccessDeniedException("Você não tem permissão para alterar essa tarefa");
            }
            ent.setStatus(novo);
            return true;
        }).orElse(false);
    }

    public List<ToDo> listarPorStatus(Status s) {
        return repo.findAllByStatusAndUsuarioLogado(s);
    }

    public List<ToDo> listarPorPriority(Priority p) {
        return repo.findAllByPriorityAndUsuarioLogado(p);
    }

    public boolean alterarPrioridade(long id, Priority newPriority) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioEntity usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return repo.findById(id).map(ent -> {
            if (!ent.getUsuario().getId().equals(usuario.getId())) {
                throw new AccessDeniedException("Você não tem permissão para alterar essa tarefa");
            }
            ent.setPriority(newPriority);
            return true;
        }).orElse(false);

    }



    public boolean editarDescricao(long id, String novaDesc) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioEntity usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return repo.findById(id).map(ent -> {
            if (!ent.getUsuario().getId().equals(usuario.getId())) {
                throw new AccessDeniedException("Você não tem permissão para alterar essa tarefa");
            }
            ent.setDescricao(novaDesc);
            return true;
        }).orElse(false);

    }

    public boolean remover(long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioEntity usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return repo.findById(id).map(ent -> {
            if (!ent.getUsuario().getId().equals(usuario.getId())) {
                throw new AccessDeniedException("Você não tem permissão para alterar essa tarefa");
            }
            repo.deleteById(id);
            return true;
        }).orElse(false);
    }

    public boolean alterarDueDate(Long id, LocalDate dueDate) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioEntity usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return repo.findById(id).map(ent -> {
            if (!ent.getUsuario().getId().equals(usuario.getId())) {
                throw new AccessDeniedException("Você não tem permissão para alterar essa tarefa");
            }
            ent.setDueDate(dueDate);
            return true;
        }).orElse(false);


    }
}