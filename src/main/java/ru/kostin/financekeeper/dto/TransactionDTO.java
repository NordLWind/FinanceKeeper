package ru.kostin.financekeeper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TransactionDTO implements Dto {
    private long id;
    private String source;
    private String target;
    private String amount;
    private String description;
    private String date;

    public Long getId() {
        return id;
    }
}
