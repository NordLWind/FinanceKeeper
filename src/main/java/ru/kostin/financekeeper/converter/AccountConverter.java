package ru.kostin.financekeeper.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.kostin.financekeeper.dto.AccountDTO;
import ru.kostin.financekeeper.entity.Account;

@Service
public class AccountConverter implements Converter<Account, AccountDTO> {
    @Override
    public AccountDTO convert(Account source) {
        AccountDTO target = new AccountDTO();
        target.setId(source.getId());
        target.setName(source.getName());
        target.setBalance(source.getBalance());
        return target;
    }
}
