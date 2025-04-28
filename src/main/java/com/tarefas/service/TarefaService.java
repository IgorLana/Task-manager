package com.tarefas.service;
import com.tarefas.model.Tarefa;

import com.tarefas.repository.TarefaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;
import java.util.Collections;

public class TarefaService {

    private static final Logger log = LoggerFactory.getLogger(TarefaService.class);

    private final TarefaRepository repo;
    private final List<Tarefa> tarefas;

    public TarefaService() {
        this.repo = new TarefaRepository();
        this.tarefas = repo.carregar();
    }



    public void adicionar(String descricao) {
        if (descricao.isEmpty()) {
            System.out.println("❌ Descrição não pode ser vazia.");
            log.warn("Usuário tentou adicionar tarefa sem descrição.");
            return;
        }
        tarefas.add(new  Tarefa(descricao));
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

    public void concluirTarefa(int num) {
        try {
            int indice = num - 1;
            if (indice < 0 || indice >= tarefas.size()) {
                System.out.println("❌ Número inválido.");
                //noinspection LoggingSimilarMessage
                log.warn("Tentativa de concluir tarefa invalida: {}", num);
                return;
            }
            tarefas.get(indice).marcarConcluida();
            System.out.println("Tarefa nº: " + (indice + 1) + " concluida com sucesso!");
            log.info("Tarefa concluida com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("❌ Entrada inválida. Digite um número.");
            log.warn("Tentativa de concluir tarefa invalida: {}", num);
        }
    }
    public void salvar() {
        repo.salvar(tarefas);
    }

    public List<Tarefa> listar() {                    // ← NOVO MÉTODO
        return Collections.unmodifiableList(tarefas); // lista somente-leitura
    }

}