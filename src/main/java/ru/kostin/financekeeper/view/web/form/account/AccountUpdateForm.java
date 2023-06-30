package ru.kostin.financekeeper.view.web.form.account;

import lombok.Data;
import ru.kostin.financekeeper.utils.ModParam;

@Data
public class AccountUpdateForm {
    private int id;
    private ModParam param;
    private String val;
}
