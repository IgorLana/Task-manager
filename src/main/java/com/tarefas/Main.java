package com.tarefas;
import com.tarefas.controller.TarefaController;

import com.tarefas.service.TarefaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Scanner;


public class Main {

    TarefaService service = new TarefaService();
    TarefaController controller = new TarefaController();
    private final Scanner scanner = new Scanner(System.in);
    private static final Logger log = LoggerFactory.getLogger(Main.class);



    public int mostrarMenu(){
        System.out.println("\n1‑Adicionar  2‑Listar  3‑Concluir  0‑Sair");
        System.out.print("Selecione uma opção: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Opção inválida.");
            log.warn("Entrada inválida no menu.");
            return -1;
        }
    }

    private void listarTarefas() {
        controller.listarTarefas();
    }

    private void adicionar() {
        System.out.print("Insira descricao: ");
        String descricao = scanner.nextLine().trim();
        controller.adicionarTarefa(descricao);

    }

    private void concluirTarefa() {
        System.out.println("\n--- Selecione a tarefa para concluir ---");
        listarTarefas();
        int num = Integer.parseInt(scanner.nextLine());
        controller.concluirTarefa(num);

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
                case 2 -> app.listarTarefas();
                case 3 -> app.concluirTarefa();
                case 0 -> System.out.println("Encerrando...");
                default -> {
                    System.out.println("❌ Opção inválida.");
                    log.warn("Entrada invalida no mostrarMenu: {}", opcao);
                }
            }
        } while (opcao != 0);
        scanner.close();
        app.controller.salvar();

    }

}
