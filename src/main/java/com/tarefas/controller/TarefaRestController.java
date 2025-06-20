package com.tarefas.controller;


import com.tarefas.dto.TarefaRequestDTO;
import com.tarefas.dto.TarefaResponseDTO;
import com.tarefas.entity.UsuarioEntity;
import com.tarefas.model.Priority;
import com.tarefas.model.Status;
import com.tarefas.repository.UsuarioRepository;
import com.tarefas.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaRestController {
    private final TarefaService service;
    private final UsuarioRepository usuarioRepository;

    public TarefaRestController(TarefaService service, UsuarioRepository usuarioRepository){
        this.service = service;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public List<TarefaResponseDTO> listarTodas(){
        return  service.listar().stream()
                .map(t -> new TarefaResponseDTO(t.getId(), t.getDescricao(), t.getStatus(), t.getPriority(), t.getDueDate()))
                .toList();
    }

    @GetMapping("/status/{status}")
    public List<TarefaResponseDTO> listarPorStatus(@PathVariable("status") Status status ){
        return service.listarPorStatus(status).stream()
                .map(t -> new TarefaResponseDTO(t.getId(), t.getDescricao(), t.getStatus(), t.getPriority(), t.getDueDate()))
                .toList();
    }

    @GetMapping("/priority/{priority}")
    public List<TarefaResponseDTO> listarPorPriority(@PathVariable("priority") Priority priority ){
        return service.listarPorPriority(priority).stream()
                .map(t -> new TarefaResponseDTO(t.getId(), t.getDescricao(), t.getStatus(), t.getPriority(), t.getDueDate()))
                .toList();
    }

    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody @Valid TarefaRequestDTO dto) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioEntity usuario = usuarioRepository.findByUsername(username).orElseThrow();


        service.adicionar(dto.descricao(), dto.priority(), dto.dueDate(), usuario);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{id}/status")
    public ResponseEntity<Void> alterarStatus(@PathVariable("id") Long id, @RequestParam("status") Status status){
        boolean ok = service.alterarStatus(id, status);
        return ok ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/priority")
    public ResponseEntity<Void> alterarPrioridade(@PathVariable("id") Long id, @RequestParam("priority") Priority priority){
        boolean ok = service.alterarPrioridade(id, priority);
        return ok ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/descricao")
    public ResponseEntity<Void> alterarDescricao(@PathVariable("id") Long id, @RequestParam("descricao") String descricao){
        boolean ok = service.editarDescricao(id, descricao);
        return ok ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }


    @PutMapping("/{id}/duedate")
    public ResponseEntity<Void> alterarDueDate(@PathVariable("id") Long id, @RequestParam("dueDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate){
        boolean ok = service.alterarDueDate(id, dueDate); // Você precisará criar este método no seu TarefaService do backend
        return ok ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable("id") Long id){
        boolean ok = service.remover(id);
        return ok ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}
