package ru.kostin.financekeeper.view.api.json.account;

import lombok.Data;
import ru.kostin.financekeeper.utils.ModParam;

@Data
public class AccountUpdateRequest {
    private int id;
    private ModParam param;
    private String val;
}
