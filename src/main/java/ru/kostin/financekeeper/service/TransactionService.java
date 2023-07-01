package ru.kostin.financekeeper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.kostin.financekeeper.dto.TransactionDTO;
import ru.kostin.financekeeper.entity.Account;
import ru.kostin.financekeeper.entity.Transaction;
import ru.kostin.financekeeper.exception.BalanceException;
import ru.kostin.financekeeper.exception.ItemNotExistException;
import ru.kostin.financekeeper.exception.SameAccountsException;
import ru.kostin.financekeeper.repository.AccountRepository;
import ru.kostin.financekeeper.repository.TransactionRepository;
import ru.kostin.financekeeper.repository.TypeRepository;
import ru.kostin.financekeeper.repository.UserRepository;
import ru.kostin.financekeeper.utils.format.BalanceFormatter;
import ru.kostin.financekeeper.utils.format.DateFormatter;
import ru.kostin.financekeeper.utils.TransactionFilter;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private static final String DEFAULT_DATE_MARKER = "0";
    private final TransactionRepository transactionRepo;
    private final UserRepository userRepo;
    private final TypeRepository typeRepo;
    private final AccountRepository accountRepo;
    private final Converter<Transaction, TransactionDTO> converter;
    private final BalanceFormatter balanceFormatter;
    private final DateFormatter dateFormatter;

    public List<TransactionDTO> getAll(Long userId) {
        return transactionRepo.findByOwner_Id(userId).stream().map(converter::convert).collect(Collectors.toList());
    }

    public List<TransactionDTO> getReport(String after, String before, long typeId, long userId) throws ParseException {
        TransactionFilter filter = new TransactionFilter();
        if (!Objects.equals(after, DEFAULT_DATE_MARKER)) {
            filter.setAfter(dateFormatter.format(after));
        }
        if (!Objects.equals(before, DEFAULT_DATE_MARKER)) {
            filter.setBefore(dateFormatter.format(before));
        }
        if (typeId != 0) {
            filter.setType(typeRepo.findById(typeId).orElseThrow(ItemNotExistException::new));
        }
        filter.setOwner(userRepo.findById(userId).orElseThrow(ItemNotExistException::new));

        return transactionRepo.getAllByFilter(filter).stream().map(converter::convert).collect(Collectors.toList());
    }

    public void save(long sourceId, long targetId, String amount, String description, String date, long userId, List<Long> types) throws ParseException {
        Transaction transaction = new Transaction();
        Account source = accountRepo.findById(sourceId).orElseThrow(ItemNotExistException::new);
        Account target = accountRepo.findById(targetId).orElseThrow(ItemNotExistException::new);
        transaction.setAmount(balanceFormatter.format(amount));
        checkAccounts(source, target, transaction.getAmount());

        source.setBalance(source.getBalance().subtract(transaction.getAmount()));
        target.setBalance(target.getBalance().add(transaction.getAmount()));
        transaction.setSource(source);
        transaction.setTarget(target);
        transaction.setDescription(description);
        transaction.setOwner(userRepo.findById(userId).orElseThrow(ItemNotExistException::new));
        transaction.setTime(date.equals(DEFAULT_DATE_MARKER) ? new Date() : dateFormatter.format(date));
        transaction.setTypes(types.stream()
                .map(l -> typeRepo.findById(l).orElseThrow(ItemNotExistException::new))
                .collect(Collectors.toList()));
        transactionRepo.saveTransaction(transaction);
    }

    private void checkAccounts(Account source, Account target, BigDecimal amount) throws BalanceException {
        if (source.getId() == 0) {
            return;
        }

        if (Objects.equals(source.getId(), target.getId())) {
            throw new SameAccountsException();
        }

        if (source.getBalance().subtract(amount).signum() == -1) {
            throw new BalanceException();
        }
    }
}
