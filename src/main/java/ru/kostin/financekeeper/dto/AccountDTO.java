package ru.kostin.financekeeper.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDTO {
    private long id;
    private String name;
    private BigDecimal balance;
}
