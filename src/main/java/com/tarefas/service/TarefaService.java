package com.tarefas.service;
import com.tarefas.model.Priority;
import com.tarefas.model.Status;
import com.tarefas.model.Tarefa;

import com.tarefas.repository.TarefaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class TarefaService {

    private static final Logger log = LoggerFactory.getLogger(TarefaService.class);

    private final TarefaRepository repo;
    private final List<Tarefa> tarefas;

    public TarefaService() {
        this.repo = new TarefaRepository();
        this.tarefas = repo.carregar();
    }



    public void adicionar(String descricao, LocalDate dueDate, Priority priority) {
        if (descricao.isEmpty()) {
            System.out.println("❌ Descrição não pode ser vazia.");
            log.warn("Usuário tentou adicionar tarefa sem descrição.");
            return;
        }

        tarefas.add(new  Tarefa(descricao, dueDate, priority));
        System.out.println("Tarefa adiciona com sucesso!");
        log.info("Tarefa adicionada: {}", descricao);
    }

    public void listarTarefas() {

        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa cadastrada");
        } else {
            System.out.println("\n--- Lista de Tarefas ---");
            for (int i = 0; i < tarefas.size(); i++) {
                System.out.println((i + 1) + " - " + tarefas.get(i));
            }
            System.out.println("--- ---------------- ---");}
    }




    public void listarPorStatus(Status s) {
        List<Tarefa> lista = tarefas.stream()
                .filter(t -> t.getStatus() == s)
                .collect(Collectors.toUnmodifiableList());
        lista.forEach(System.out::println);
    }

    public void listarPorPrioridade(Priority p) {
        List<Tarefa> lista = tarefas.stream()
                .filter(t -> t.getPrioridade() == p)
                .collect(Collectors.toUnmodifiableList());
        lista.forEach(System.out::println);
    }

    private void validarIndice(int idx) {
        if (idx < 0 || idx >= tarefas.size()) {
            throw new IllegalArgumentException("Índice de tarefa inválido: " + (idx + 1));
        }
    }


    private boolean alterar(int num, Consumer<Tarefa> op, String logMsg) {
        int idx = num - 1;
        try {
            validarIndice(idx);
            op.accept(tarefas.get(idx));
            System.out.println("Tarefa nº " + num + " alterada com sucesso!");
            log.info(logMsg);
            return true;
        } catch (IllegalArgumentException ex) {
            System.out.println("❌ " + ex.getMessage());
            log.warn(ex.getMessage());
            return false;
        }
    }

    public boolean alterarPrioridade(int num, Priority newPriority) {
        return alterar(
                num,
                t -> t.setPriority(newPriority),
                "Prioridade alterada para " + newPriority
        );
    }

    public boolean alterarDescricao(int num, String novaDescricao) {
        return alterar(
                num,
                tarefa -> tarefa.setDescricao(novaDescricao),
                "descrição alterada para" + novaDescricao
        );
    }

    public boolean alterarStatus(int num, Status novoStatus) {
    return alterar(
            num,
            t -> t.setStatus(novoStatus),
            "Status alterado para " + novoStatus.getRotulo()
    );

    }
    public void salvar() {
        repo.salvar(tarefas);
    }

    public List<Tarefa> listar() {                    // ← NOVO MÉTODO
        return Collections.unmodifiableList(tarefas); // lista somente-leitura
    }

}