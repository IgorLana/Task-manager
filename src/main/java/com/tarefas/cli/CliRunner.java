package com.tarefas.cli;

import com.tarefas.controller.TarefaController;
import com.tarefas.model.*;
import com.tarefas.util.InputUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Interface de linha de comando que interage com o usuário.
 * Depende apenas do TarefaController (injetado pelo Spring).
 */
@Component
public class CliRunner implements CommandLineRunner {

    private final TarefaController controller;
    private final Scanner scanner = new Scanner(System.in);

    public CliRunner(TarefaController controller) {
        this.controller = controller;
    }

    @Override
    public void run(String... args) {
        int op;
        do {
            op = InputUtils.readInt(scanner,
                    """
                    \n╔════════ MENU ════════╗
                    1  Adicionar tarefa
                    2  Listar tarefas
                    3  Alterar status
                    4  Alterar prioridade
                    5  Editar descrição
                    6  Remover tarefa
                    7  Filtrar por status
                    8  Filtrar por prioridade
                    0  Sair
                    ╚══════════════════════╝
                    Escolha: """, -1);

            switch (op) {
                case 1 -> adicionar();
                case 2 -> listar(controller.listarTarefas());
                case 3 -> alterarStatus();
                case 4 -> alterarPrioridade();
                case 5 -> editarDescricao();
                case 6 -> remover();
                case 7 -> listar(controller.listarPorStatus(escolherStatus()));
                case 8 -> listar(controller.listarPorPriority(escolherPrioridade()));
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("❌ Opção inválida.");
            }
        } while (op != 0);
    }

    /* ----------------- Ações ----------------- */

    private void adicionar() {
        String desc = InputUtils.readNonEmpty(scanner, "Descrição: ");
        Priority prio = escolherPrioridade();
        LocalDate due = lerData();

    }

    private void alterarStatus() {

    }

    private void alterarPrioridade() {

    }

    private void editarDescricao() {

    }

    private void remover() {

    }

    /* -------------- Helpers UI -------------- */

    private void listar(List<ToDo> lista) {
        if (lista.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
            return;
        }
        System.out.println("\n--- Tarefas ---");
        for (int i = 0; i < lista.size(); i++) {
            System.out.printf("%d) %s%n", i + 1, lista.get(i));
        }
    }



    private Priority escolherPrioridade() {
        int op = InputUtils.readInt(scanner, "1-Baixa 2-Média 3-Alta: ", -1);
        return switch (op) {
            case 1 -> Priority.LOW;
            case 2 -> Priority.MEDIUM;
            case 3 -> Priority.HIGH;
            default -> Priority.LOW;
        };
    }

    private Status escolherStatus() {
        int op = InputUtils.readInt(scanner,
                "1-Pendente 2-Em andamento 3-Adiada 4-Concluída: ", -1);
        return switch (op) {
            case 2 -> Status.EM_ANDAMENTO;
            case 3 -> Status.ADIADA;
            case 4 -> Status.CONCLUIDA;
            default -> Status.PENDENTE;
        };
    }

    private LocalDate lerData() {
        String txt = InputUtils.readNonEmpty(scanner, "Data limite (yyyy-MM-dd) ou vazio: ");
        if (txt.isBlank()) return null;
        try {
            return LocalDate.parse(txt);
        } catch (DateTimeParseException e) {
            System.out.println("Formato inválido. Usando valor nulo.");
            return null;
        }
    }
}
