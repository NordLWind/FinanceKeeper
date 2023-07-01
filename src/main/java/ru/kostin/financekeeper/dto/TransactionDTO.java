package ru.kostin.financekeeper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.kostin.financekeeper.utils.TableContent;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TransactionDTO implements Dto {
    @TableContent("№")
    private long id;

    @TableContent("Откуда")
    private String source;

    @TableContent("Куда")
    private String target;

    @TableContent("Сумма")
    private String amount;

    @TableContent("Описание")
    private String description;

    @TableContent("Время")
    private String date;

    public Long getId() {
        return id;
    }
}
