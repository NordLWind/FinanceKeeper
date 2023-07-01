package ru.kostin.financekeeper.api.json.transaction;

import lombok.Data;

@Data
public class TransactionReportRequest {
    private String dAfter;
    private String dBefore;
    private int type;
}
