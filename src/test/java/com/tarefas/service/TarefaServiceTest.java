package com.tarefas.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Collections;
import java.util.List;



class TarefaServiceTest {

    @Test
    void adicionarIncrementaLista() {
        TarefaService service = new TarefaService();
        int antes = service.listar().size();
        service.adicionar("Estudar JUnit");
        assertEquals(antes + 1, service.listar().size(),
                "Quantidade de tarefas não incrementou");
    }
}
