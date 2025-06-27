package com.tarefas.controller;

import com.tarefas.dto.TaskRequestDTO;
import com.tarefas.dto.TaskResponseDTO;
import com.tarefas.entity.TaskEntity;
import com.tarefas.entity.UsuarioEntity;
import com.tarefas.repository.UsuarioRepository;
import com.tarefas.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/task")
public class TaskRestController {
    private final TaskService service;
    private final UsuarioRepository usuarioRepository;

    public TaskRestController(TaskService service, UsuarioRepository usuarioRepository){
        this.service = service;
        this.usuarioRepository = usuarioRepository;
    }


    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody @Valid TaskRequestDTO dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioEntity usuario = usuarioRepository.findByUsername(username).orElseThrow();
        service.salvarTask(dto.priority(), dto.descricao(), dto.dueDate(), dto.title(), dto.pdca(), dto.checklist(), usuario);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}") // Ou @PatchMapping para atualizações parciais
    public ResponseEntity<TaskEntity> update(@PathVariable("id") String id, @RequestBody @Valid TaskRequestDTO dto) {
        service.updateTask(id, dto.priority(), dto.descricao(), dto.dueDate(), dto.title(), dto.pdca(), dto.checklist());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<TaskResponseDTO> listarTodas(){
        return  service.listarTask().stream()
                .map(t -> new TaskResponseDTO(t.getId(),t.getDescricao(), t.getPriority(), t.getTitle(), t.getPdca(), t.getChecklist(), t.getDueDate()))
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable("id") String  id){
        boolean ok = service.remover(id);
        return ok ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }



}
