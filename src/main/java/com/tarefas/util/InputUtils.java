package com.tarefas.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public final class InputUtils {

    private static final Logger log = LoggerFactory.getLogger(InputUtils.class);

    /**
     * Lê um inteiro do Scanner; se inválido devolve um valor sentinel.
     * @param sc      Scanner compartilhado da CLI
     * @param prompt  mensagem para o usuário
     * @param fallback valor devolvido em caso de erro (ex.: -1)
     * @return int válido ou fallback
     */
    public static int readInt(Scanner sc, String prompt, int fallback){
        System.out.println(prompt);
        String entrada = sc.nextLine();

        try {

            return Integer.parseInt(entrada.trim());
        } catch (NumberFormatException e){
           System.out.println("❌ Entrada inválida, tente novamente.");
            log.warn("Entrada numérica inválida: '{}'", entrada);
            return fallback;
        }
    }


    public static String readNonEmpty(Scanner sc, String prompt){
        while(true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if(!s.isEmpty()) return s;
            System.out.println("Campo vazio, tente novamente.");
        }
    }




}
