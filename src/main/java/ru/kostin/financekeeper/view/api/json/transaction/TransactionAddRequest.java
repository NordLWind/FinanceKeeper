package ru.kostin.financekeeper.view.api.json.transaction;

import lombok.Data;

@Data
public class TransactionAddRequest {
    private int idSource;
    private int idTarget;
    private String amount;
    private String date;
    private String description;
    private int type;
}
