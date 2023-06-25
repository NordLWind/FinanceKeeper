package ru.kostin.financekeeper.utils;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kostin.financekeeper.entity.Type;
import ru.kostin.financekeeper.entity.User;

import java.util.Date;

@Data
@NoArgsConstructor
public class TransactionFilter {
    private Date after;
    private Date before;
    private Type type;
    private User owner;
}
