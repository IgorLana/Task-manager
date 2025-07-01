package com.tarefas.service;

import com.tarefas.entity.ToDoEntity;
import com.tarefas.entity.UsuarioEntity;
import com.tarefas.model.*;
import com.tarefas.repository.ToDoRepository;
import com.tarefas.repository.UsuarioRepository;


import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ToDoService {

    private final ToDoRepository repo;
    private final UsuarioRepository usuarioRepository;
    public ToDoService(ToDoRepository repo, UsuarioRepository usuarioRepository) {
        this.repo = repo;
        this.usuarioRepository = usuarioRepository;
    }

    public void adicionar(String desc, Priority prio, LocalDate dueDate, UsuarioEntity usuario) {
        ToDoEntity entity = new ToDoEntity(null, desc, Status.PENDENTE, prio, dueDate, CardType.TODO);
        entity.setUser(usuario); // associa a tarefa ao usuário logado
        repo.save(entity);
    }

    private ToDo toModel(ToDoEntity e) {
        return new ToDo(e.getId(), e.getDescricao(),e.getStatus(), e.getDueDate(), e.getPriority(), e.getCardType());

    }

    public List<ToDo> listar() {
        UsuarioEntity usuario = getUsuarioLogado();
        return repo.findByUser(usuario).stream()
                .map(this::toModel)
                .filter(todo -> todo.getCardType() == CardType.TODO)
                .collect(Collectors.toList());
    }

    public boolean alterarStatus(String id, Status novo) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioEntity usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return repo.findById(id).map(ent -> {
            if (!ent.getUser().getId().equals(usuario.getId())) {
                throw new AccessDeniedException("Você não tem permissão para alterar essa tarefa");
            }
            ent.setStatus(novo);
            return true;
        }).orElse(false);
    }

    public List<ToDo> listarPorStatus(Status s) {
        UsuarioEntity usuario = getUsuarioLogado();
        return repo.findByStatusAndUser(s, usuario).stream() // Usando findByStatusAndUser
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public List<ToDo> listarPorPriority(Priority p) {
        UsuarioEntity usuario = getUsuarioLogado();
        return repo.findByPriorityAndUser(p, usuario).stream() // Usando findByPriorityAndUser
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public boolean alterarPrioridade(String id, Priority newPriority) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioEntity usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return repo.findById(id).map(ent -> {
            if (!ent.getUser().getId().equals(usuario.getId())) {
                throw new AccessDeniedException("Você não tem permissão para alterar essa tarefa");
            }
            ent.setPriority(newPriority);
            repo.save(ent);
            return true;
        }).orElse(false);

    }



    public boolean editarDescricao(String id, String novaDesc) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioEntity usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return repo.findById(id).map(ent -> {
            if (!ent.getUser().getId().equals(usuario.getId())) {
                throw new AccessDeniedException("Você não tem permissão para alterar essa tarefa");
            }
            ent.setDescricao(novaDesc);
            return true;
        }).orElse(false);

    }

    public boolean remover(String  id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioEntity usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return repo.findById(id).map(ent -> {
            if (!ent.getUser().getId().equals(usuario.getId())) {
                throw new AccessDeniedException("Você não tem permissão para alterar essa tarefa");
            }
            repo.deleteById(id);
            return true;
        }).orElse(false);
    }

    public boolean alterarDueDate(String id, LocalDate dueDate) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioEntity usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return repo.findById(id).map(ent -> {
            if (!ent.getUser().getId().equals(usuario.getId())) {
                throw new AccessDeniedException("Você não tem permissão para alterar essa tarefa");
            }
            ent.setDueDate(dueDate);
            return true;
        }).orElse(false);
    }


    public void updateTask(String id, Priority priority, String descricao, LocalDate dueDate, Status status) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioEntity usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        repo.findById(id).map(ent -> {
            if (!ent.getUser().getId().equals(usuario.getId())) {
                throw new AccessDeniedException("Você não tem permissão para alterar essa tarefa");
            }
            ent.setDescricao(descricao);
            ent.setPriority(priority);
            ent.setDueDate(dueDate);
            ent.setStatus(status);
            repo.save(ent);// Atualiza o checklist completo
            return true;
        });
    }

    private UsuarioEntity getUsuarioLogado() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByUsername(username) // Assumindo que findByUsername agora é findByEmail
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }
}