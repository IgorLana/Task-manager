package com.tarefas.service;

import com.tarefas.entity.TaskEntity;
import com.tarefas.entity.UsuarioEntity;
import com.tarefas.model.*;
import com.tarefas.repository.TaskRepository;
import com.tarefas.repository.UsuarioRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UsuarioRepository usuarioRepository;
    public TaskService(TaskRepository taskRepository, UsuarioRepository usuarioRepository) {
        this.taskRepository = taskRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void salvarTask(Priority priority, String descricao, LocalDate dueDate, String title, Pdca pdca, List<ChecklistItem> checklist, UsuarioEntity usuario) {
        TaskEntity task = new TaskEntity(null, priority, descricao, dueDate, title, pdca, checklist, CardType.TASK);
        task.setUser(usuario);
        taskRepository.save(task);
    }

    public void updateTask(String id, Priority priority, String descricao, LocalDate dueDate, String title, Pdca pdca, List<ChecklistItem> checklist) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioEntity usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        taskRepository.findById(id).map(ent -> {
            if (!ent.getUser().getId().equals(usuario.getId())) {
                throw new AccessDeniedException("Você não tem permissão para alterar essa tarefa");
            }
            ent.setDescricao(descricao);
            ent.setPriority(priority);
            ent.setDueDate(dueDate);
            ent.setTitle(title);
            ent.setPdca(pdca);
            ent.setChecklist(checklist);
            taskRepository.save(ent);// Atualiza o checklist completo
            return true;
        });
    }

    public boolean remover(String  id) {
        UsuarioEntity usuario = getUsuarioLogado();

        return taskRepository.findById(id).map(ent -> {
            if (!ent.getUser().getId().equals(usuario.getId())) {
                throw new AccessDeniedException("Você não tem permissão para alterar essa tarefa");
            }
            taskRepository.deleteById(id);
            return true;
        }).orElse(false);
    }
    
    

    private Task toModel(TaskEntity e) {
        return new Task(e.getId(), e.getPriority(), e.getDescricao(), e.getDueDate(), e.getTitle(), e.getPdca(), e.getChecklist(), e.getCardType());
    }

    public List<Task> listarTask() {
        UsuarioEntity usuario = getUsuarioLogado();
        return taskRepository.findByUser(usuario).stream()
                .map(this::toModel)
                .filter(task -> task.getCardType().equals(CardType.TASK))
                .collect(Collectors.toList());
    }

    private UsuarioEntity getUsuarioLogado() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByUsername(username) // Assumindo que findByUsername agora é findByEmail
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }

}
