package com.tarefas;
import com.tarefas.controller.TarefaController;

import com.tarefas.model.Priority;
import com.tarefas.model.Status;

import com.tarefas.util.InputUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;



public class Main {


    TarefaController controller = new TarefaController();
    private final Scanner scanner = new Scanner(System.in);
    private static final Logger log = LoggerFactory.getLogger(Main.class);



    public int mostrarMenu(){
        System.out.println("\n1‑Adicionar  2‑Listar  3‑Alterar o status 4-Alterar a prioridade 5-Editar Descrição 0‑Sair");
        System.out.print("Selecione uma opção: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Opção inválida.");
            log.warn("Entrada inválida no menu.");
            return -1;
        }
    }

    private void listarTarefas(){controller.listarTarefas();}

    private void filtrarTarefas() {
        int op = InputUtils.readInt(scanner, "Filtre as tarefas por: 1-Ver todas 2‑Filtro por Status  3‑ Filtro por Prioridade ", -1);

        switch (op) {
            case 1 -> controller.listarTarefas();
            case 2 -> {
                System.out.println("\n--- Selecione o status para filtrar ---");
                int opStatus = InputUtils.readInt(scanner, "1- Pendente 2-Em andamento 3-Adiado 4-Concluido", -1);
                switch (opStatus) {
                    case 1 -> controller.listarPorStatus(Status.PENDENTE);
                    case 2 -> controller.listarPorStatus(Status.EM_ANDAMENTO);
                    case 3 -> controller.listarPorStatus(Status.ADIADA);
                    case 4 -> controller.listarPorStatus(Status.CONCLUIDA);
                    default -> System.out.println("Opção invalida");
                }}
            case 3 -> {
                System.out.println("\n--- Selecione a prioridade para filtrar ---");
                int opPrioridade = InputUtils.readInt(scanner, "1- Alta 2-Média 3-Baixa", -1);
                switch (opPrioridade) {
                    case 1 -> controller.listarPorPrioridade(Priority.HIGH);
                    case 2 -> controller.listarPorPrioridade(Priority.MEDIUM);
                    case 3 -> controller.listarPorPrioridade(Priority.LOW);
                    default -> System.out.println("Opção invalida");
                }}
            default -> System.out.println("Opção invalida");
        }
    }

    private void adicionar() {
        System.out.print("Insira descricao: ");
        String descricao = scanner.nextLine().trim();
        String dataStr = InputUtils.readNonEmpty(scanner, "Data (yyyy-MM-dd): ");
        LocalDate date = null;

        try {
            date = LocalDate.parse(dataStr);
        }catch (DateTimeParseException e){
            System.out.println("Formato invalido. Use yyyy-mm-dd.");
        }
        Priority priority = Priority.LOW;

        int opPriority = InputUtils.readInt(scanner, "1- Alto 2- Médio 3- Baixo", -1);
        switch (opPriority) {
            case 1 -> priority = Priority.HIGH;
            case 2 -> priority = Priority.MEDIUM;
            case 3 -> priority = Priority.LOW;
            default -> System.out.println("Opção invalida");
        }



        controller.adicionarTarefa(descricao, date, priority);



    }

    private void alterarPrioridade() {
        System.out.println("\n--- Selecione a tarefa para alterar a prioridade ---");
        listarTarefas();
        int num = InputUtils.readInt(scanner, "", -1);


        int op = InputUtils.readInt(scanner, "1-Baixa 2-Média 3-Alta", -1);


        Priority newPriority;


        switch (op) {
            case 1 -> newPriority = Priority.LOW;
            case 2 -> newPriority = Priority.MEDIUM;
            case 3 -> newPriority =  Priority.HIGH;
            default -> {System.out.println("Opção invalida"); return;}
        }
        if (controller.alterarPrioridade(num, newPriority)) {
            System.out.println("✅ prioridade alterado com sucesso!");
        } else {
            System.out.println("❌Não foi possivel alterar a prioridade");
        }

    }

    private void alterarDescricao() {

        listarTarefas();
        int num = InputUtils.readInt(scanner, "", -1);
        System.out.println("\n--- Selecione a tarefa para alterar o status ---");
        System.out.println("\n--- Nova descrição: ---");
        String novaDescricao = scanner.nextLine().trim();

        if (controller.alterarDescricao(num, novaDescricao)) {
            System.out.println("✅ Descrição alterado com sucesso!");
        } else {
            System.out.println("❌Não foi possivel alterar a descrição");
        }

    }

    private void alterarStatus() {
        System.out.println("\n--- Selecione a tarefa para alterar o status ---");
        listarTarefas();
        int num = InputUtils.readInt(scanner, "", -1);

        int op = InputUtils.readInt(scanner, "1-Pendente 2-Em andamento 3-Adiada 4-Concluída", -1);


        Status novoStatus;

        switch (op) {
            case 1 -> novoStatus = Status.PENDENTE;
            case 2 -> novoStatus = Status.EM_ANDAMENTO;
            case 3 -> novoStatus =  Status.ADIADA;
            case 4 -> novoStatus = Status.CONCLUIDA;
            default -> {System.out.println("Opção invalida"); return;}
        }

        if (controller.alterarStatus(num, novoStatus)) {
            System.out.println("✅ Status alterado com sucesso!");
        } else {
            System.out.println("❌Não foi possivel alterar o status");
        }

    }

    public static void main(String[] args) {

        Main app = new Main();
        log.info("Aplicação iniciada!");

        int opcao;
        Scanner scanner = new Scanner(System.in);

        do {
            opcao = app.mostrarMenu();
            switch (opcao) {
                case 1 -> app.adicionar();
                case 2 -> app.filtrarTarefas();
                case 3 -> app.alterarStatus();
                case 4 -> app.alterarPrioridade();
                case 5 -> app.alterarDescricao();
                case 0 -> System.out.println("Encerrando...");
                default -> {
                    System.out.println("❌ Opção inválida.");
                    log.warn("Entrada invalida no mostrarMenu: {}", opcao);
                }
            }
            app.controller.salvar();
        } while (opcao != 0);
        scanner.close();
        app.controller.salvar();

    }

}
