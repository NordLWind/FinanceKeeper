package ru.kostin.financekeeper.web.form.account;

import lombok.Data;

@Data
public class AccountUpdateForm {
    private int id;
    private String param;
    private String val;
}
