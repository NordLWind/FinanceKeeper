package ru.kostin.financekeeper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.kostin.financekeeper.dto.AccountDTO;
import ru.kostin.financekeeper.entity.Account;
import ru.kostin.financekeeper.exception.ItemAlreadyExistsException;
import ru.kostin.financekeeper.exception.ItemNotExistException;
import ru.kostin.financekeeper.repository.AccountRepository;
import ru.kostin.financekeeper.repository.UserRepository;
import ru.kostin.financekeeper.utils.format.BalanceFormatter;
import ru.kostin.financekeeper.utils.ModParam;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepo;
    private final UserRepository userRepo;
    private final Converter<Account, AccountDTO> converter;
    private final BalanceFormatter formatter;

    public AccountDTO get(long id) {
        return converter.convert(accountRepo.findById(id).orElseThrow(ItemNotExistException::new));
    }

    public List<AccountDTO> getAll(long userId) {
        return accountRepo.findByOwner_Id(userId).stream().sorted(Comparator.comparingLong(Account::getId)).map(converter::convert).collect(Collectors.toList());
    }

    public void save(String name, String balance, long userId) {
        if (accountRepo.existsByNameAndOwner_Id(name, userId)) {
            throw new ItemAlreadyExistsException();
        }
        Account account = new Account();
        account.setName(name);
        account.setBalance(new BigDecimal(balance));
        account.setOwner(userRepo.findById(userId).orElseThrow(ItemNotExistException::new));
        accountRepo.save(account);
    }

    public void delete(long id) {
        if (!accountRepo.existsById(id)) {
            throw new ItemNotExistException();
        }
        accountRepo.deleteById(id);
    }

    public void update(long id, ModParam param, String value, long userId) {
        Account account = accountRepo.findById(id).orElseThrow(ItemNotExistException::new);
        if (param == ModParam.BALANCE) {
            account.setBalance(formatter.format(value));
            accountRepo.save(account);
            return;
        }

        if (accountRepo.existsByNameAndOwner_Id(value, userId)) {
            throw new ItemAlreadyExistsException();
        }

        account.setName(value);
        accountRepo.save(account);
    }
}
