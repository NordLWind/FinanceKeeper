package ru.kostin.financekeeper.dto;

import lombok.Data;

@Data
public class TransactionDTO implements Dto {
    private long id;
    private String source;
    private String target;
    private String amount;
    private String description;

    public Long getId() {
        return id;
    }
}
