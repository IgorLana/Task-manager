package com.tarefas.persistence;

import com.tarefas.entity.TarefaEntity;
import com.tarefas.model.Priority;
import com.tarefas.model.Status;
import com.tarefas.repository.JpaTarefaRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
public class TarefaRepositoryTest {

    @Autowired
    private JpaTarefaRepository repo;

    @Test
    @DisplayName("Deve retornar tarefas pelo status")
    public void testSalvarTarefa() {
        var tarefa = new TarefaEntity();
        tarefa.setDescricao("Estudar Java");
        tarefa.setPriority(Priority.HIGH);
        tarefa.setStatus(Status.EM_ANDAMENTO);
        tarefa.setDueDate(LocalDate.now().plusDays(1));
        repo.save(tarefa);

        var tarefa2 = new TarefaEntity();
        tarefa2.setDescricao("Estudar Spring Boot");
        tarefa2.setPriority(Priority.MEDIUM);
        tarefa2.setStatus(Status.PENDENTE);
        tarefa2.setDueDate(LocalDate.now().plusDays(2));
        repo.save(tarefa2);

        List<TarefaEntity> todas = repo.findAll();
        assertThat(todas).hasSize(2);
        assertThat(todas.get(0).getDescricao()).isEqualTo("Estudar Java");

    }

    @Test
    @DisplayName("Deve retornar tarefas pelo status")
    public void testFindByStatus() {

        repo.deleteAll();

        var tarefa = new TarefaEntity();
        tarefa.setDescricao("Estudar Java");
        tarefa.setPriority(Priority.HIGH);
        tarefa.setStatus(Status.EM_ANDAMENTO);
        tarefa.setDueDate(LocalDate.now().plusDays(1));
        repo.save(tarefa);

        var tarefa2 = new TarefaEntity();
        tarefa2.setDescricao("Estudar Spring Boot");
        tarefa2.setPriority(Priority.MEDIUM);
        tarefa2.setStatus(Status.CONCLUIDA);
        tarefa2.setDueDate(LocalDate.now().plusDays(2));
        repo.save(tarefa2);

        var tarefa3 = new TarefaEntity();
        tarefa3.setDescricao("Estudar Spring");
        tarefa3.setPriority(Priority.MEDIUM);
        tarefa3.setStatus(Status.CONCLUIDA);
        tarefa3.setDueDate(LocalDate.now().plusDays(2));
        repo.save(tarefa3);

        List<TarefaEntity> concluidas = repo.findByStatus(Status.CONCLUIDA);
        List<TarefaEntity> andamento = repo.findByStatus(Status.EM_ANDAMENTO);

        assertThat(concluidas).hasSize(2);
        assertThat(concluidas)
                .extracting(TarefaEntity::getDescricao)
                .containsExactlyInAnyOrder("Estudar Spring", "Estudar Spring Boot");

        assertThat(andamento).hasSize(1);
        assertThat(andamento)
                .extracting(TarefaEntity::getDescricao)
                .containsExactlyInAnyOrder("Estudar Java");

    }

    @Test
    @DisplayName("Deve retornar tarefas pelo status")
    public void testFindByPriority() {
        repo.deleteAll();

        var tarefa = new TarefaEntity();
        tarefa.setDescricao("Estudar Java");
        tarefa.setPriority(Priority.HIGH);
        tarefa.setStatus(Status.EM_ANDAMENTO);
        tarefa.setDueDate(LocalDate.now().plusDays(1));
        repo.save(tarefa);

        var tarefa2 = new TarefaEntity();
        tarefa2.setDescricao("Estudar Spring Boot");
        tarefa2.setPriority(Priority.MEDIUM);
        tarefa2.setStatus(Status.CONCLUIDA);
        tarefa2.setDueDate(LocalDate.now().plusDays(2));
        repo.save(tarefa2);

        var tarefa3 = new TarefaEntity();
        tarefa3.setDescricao("Estudar Spring");
        tarefa3.setPriority(Priority.MEDIUM);
        tarefa3.setStatus(Status.CONCLUIDA);
        tarefa3.setDueDate(LocalDate.now().plusDays(2));
        repo.save(tarefa3);

        List<TarefaEntity> high = repo.findByPriority(Priority.HIGH);
        List<TarefaEntity> medium = repo.findByPriority(Priority.MEDIUM);

        assertThat(high).hasSize(1);
        assertThat(high)
                .extracting(TarefaEntity::getDescricao)
                .containsExactlyInAnyOrder("Estudar Java");

        assertThat(medium).hasSize(2);
        assertThat(medium)
                .extracting(TarefaEntity::getDescricao)
                .containsExactlyInAnyOrder("Estudar Spring", "Estudar Spring Boot");


    }


}