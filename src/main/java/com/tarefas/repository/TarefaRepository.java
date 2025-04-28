package com.tarefas.repository;

import com.google.gson.Gson;                   // lib JSON
import com.google.gson.reflect.TypeToken;
import com.tarefas.model.Tarefa;               // model
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.nio.file.Files;                    // I/O moderno
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class TarefaRepository {
    private static final Logger log = LoggerFactory.getLogger(TarefaRepository.class);

    Path ARQUIVO = Path.of(System.getProperty("user.home"), ".tarefas", "tasks.json");

    private static final Gson gson = new Gson();

    private static final Type LIST_TYPE = new TypeToken<List<Tarefa>>(){}.getType();

    public List<Tarefa> carregar(){
        try {
            if (Files.exists(ARQUIVO)) {
                String json = Files.readString(ARQUIVO);
                List<Tarefa> lista = gson.fromJson(json, LIST_TYPE);
                log.info("Tarefas carregadas com sucesso!");
                return lista;
            } else {
                log.info("Arquivo {} não existe. Começando vazio.", ARQUIVO);
            }
        }catch (Exception e){
            log.error("Erro ao carregar tarefas: {}", e.getMessage());
        }
        return new ArrayList<>();
    }


    public void salvar(List<Tarefa> tarefas){

        try {
            String json = gson.toJson(tarefas, LIST_TYPE);
            Files.writeString(ARQUIVO, json);
            log.info("Tarefas salva em {}", ARQUIVO.toAbsolutePath());


        } catch (Exception e) {
            log.error("Erro ao salvar tarefas: {}", e.getMessage());
        }
    }

}
