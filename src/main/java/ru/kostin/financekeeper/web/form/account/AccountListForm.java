package ru.kostin.financekeeper.web.form.account;

import lombok.Data;

import java.util.List;

@Data
public class AccountListForm {
    private List<String> accounts;
}
