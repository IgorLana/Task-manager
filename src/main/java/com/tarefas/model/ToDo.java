package com.tarefas.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ToDo extends BaseItem{

    private Status status;

    public ToDo(String id, String descricao, Status status, LocalDate dueDate, Priority priority, CardType cardType) {
        super(id, priority, descricao, dueDate, cardType);
        this.status = status;

    }
}
