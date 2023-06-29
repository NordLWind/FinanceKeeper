package ru.kostin.financekeeper.api.json.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.kostin.financekeeper.dto.AccountDTO;

import java.util.List;

@Data
@AllArgsConstructor
public class AccountListResponse {
    private List<AccountDTO> accounts;
}
