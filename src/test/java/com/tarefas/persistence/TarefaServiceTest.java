package com.tarefas.persistence;


import com.tarefas.model.Priority;
import com.tarefas.model.Status;
import com.tarefas.model.Tarefa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TarefaServiceTest {

    @Mock
    private JpaTarefaRepository repo;

    @InjectMocks
    private TarefaService service;

    private TarefaEntity tarefaEntity;

    @BeforeEach
    void setUp(){
        tarefaEntity = new TarefaEntity();
        tarefaEntity.setDescricao("Tarefa concluida");
        tarefaEntity.setStatus(Status.CONCLUIDA);
        tarefaEntity.setDueDate(LocalDate.of(2025,12,5));
        tarefaEntity.setPriority(Priority.HIGH);
    }

    @Test
    @DisplayName("Deve retornar uma lista de tarefas concluidas")
    void testListarTarefas(){

        when(repo.findByStatus(Status.CONCLUIDA)).thenReturn(List.of(tarefaEntity));

        List<Tarefa> resultado = service.listarPorStatus(Status.CONCLUIDA);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getDescricao()).isEqualTo("Tarefa concluida");
        assertThat(resultado.get(0).getPriority()).isEqualTo(Priority.HIGH);
        assertThat(resultado.get(0).getDueDate()).isEqualTo(LocalDate.of(2025,12,5));
        assertThat(resultado.get(0).getStatus()).isEqualTo(Status.CONCLUIDA);

    }

    @Test
    @DisplayName("Deve salvar uma nova tarefa")
    void testAdicionarTarefa(){

        String descricao = "Estudar mockito";
        Priority prioridade = Priority.MEDIUM;
        LocalDate vencimento = LocalDate.of(2025,5,20);

        service.adicionar(descricao, prioridade, vencimento);

        ArgumentCaptor<TarefaEntity> captor = ArgumentCaptor.forClass(TarefaEntity.class);
        verify(repo).save(captor.capture());

        TarefaEntity salva = captor.getValue();
        assertThat(salva.getDescricao()).isEqualTo(descricao);
        assertThat(salva.getPriority()).isEqualTo(prioridade);
        assertThat(salva.getDueDate()).isEqualTo(vencimento);
        assertThat(salva.getStatus()).isEqualTo(Status.PENDENTE);

    }

    @Test
    @DisplayName("Deve alterar o status de uma tarefa")
    void testAlterarStatus_IdExistente(){

        Long id = 1L;
        TarefaEntity entity = new TarefaEntity(id, "Tarefa para alterar", Status.PENDENTE, Priority.MEDIUM, LocalDate.now());

        when(repo.findById(id)).thenReturn(Optional.of(entity));

        boolean resultado = service.alterarStatus(id, Status.CONCLUIDA);

        assertThat(resultado).isTrue();
        assertThat(entity.getStatus()).isEqualTo(Status.CONCLUIDA);
        verify(repo).findById(id);

    }

    @Test
    @DisplayName("Deve retornar false se o ID não existir")
    void testAlterarStatus_IdInexistente(){
        Long id = 99L;
        when(repo.findById(id)).thenReturn(Optional.empty());

        boolean resultado = service.alterarStatus(id, Status.CONCLUIDA);

        assertThat(resultado).isFalse();
        verify(repo).findById(id);

    }

    @Test
    @DisplayName("Deve remover o ID")
    void testRemoverTarefa(){

        Long id = 1L;
        when(repo.existsById(id)).thenReturn(true);

        boolean resultado = service.remover(id);

        assertThat(resultado).isTrue();
        verify(repo).existsById(id);
        verify(repo).deleteById(id);
    }


}
