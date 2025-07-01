package com.tarefas.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public abstract  class BaseItem {
    @Id
    private String id;

    private Priority priority;
    private String descricao;
    private LocalDate dueDate;
    private CardType cardType;

    public BaseItem(String id, Priority priority, String descricao, LocalDate dueDate, CardType cardType) {
        this.id = id;
        this.priority = priority;
        this.descricao = descricao;
        this.dueDate = dueDate;
        this.cardType = cardType;
    }

}
