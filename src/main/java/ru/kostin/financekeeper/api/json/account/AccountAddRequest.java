package ru.kostin.financekeeper.api.json.account;

import lombok.Data;

@Data
public class AccountAddRequest {
    private String name;
    private String balance;
}
