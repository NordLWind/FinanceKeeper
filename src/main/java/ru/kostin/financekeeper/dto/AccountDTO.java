package ru.kostin.financekeeper.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDTO implements Dto{
    private long id;
    private String name;
    private BigDecimal balance;

    public Long getId() {
        return id;
    }
}
