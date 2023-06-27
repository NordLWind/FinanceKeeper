package ru.kostin.financekeeper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AccountDTO implements Dto {
    private long id;
    private String name;
    private BigDecimal balance;

    public Long getId() {
        return id;
    }
}
