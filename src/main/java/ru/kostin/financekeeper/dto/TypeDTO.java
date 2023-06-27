package ru.kostin.financekeeper.dto;

import lombok.Data;

@Data
public class TypeDTO implements Dto {
    private long id;
    private String type;

    public Long getId() {
        return id;
    }
}
