package ru.kostin.financekeeper.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.kostin.financekeeper.entity.Account;
import ru.kostin.financekeeper.entity.Transaction;
import ru.kostin.financekeeper.entity.User;
import ru.kostin.financekeeper.exception.ItemNotExistException;
import ru.kostin.financekeeper.utils.TransactionFilter;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class TransactionRepositoryTest {
    @Autowired
    TransactionRepository subj;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void save() {
        Transaction transaction = new Transaction();
        Account source = accountRepository.findById(1L).orElseThrow(ItemNotExistException::new);
        Account target = accountRepository.findById(2L).orElseThrow(ItemNotExistException::new);
        User owner = userRepository.findById(1L).orElseThrow(ItemNotExistException::new);
        transaction.setTarget(target);
        transaction.setSource(source);
        transaction.setAmount(new BigDecimal("100.00"));
        source.setBalance(source.getBalance().subtract(transaction.getAmount()));
        target.setBalance(target.getBalance().add(transaction.getAmount()));
        transaction.setOwner(owner);
        transaction.setTime(new Date());
        transaction.setDescription("descr");
        subj.save(transaction);

        assertEquals(new BigDecimal("100.00"), accountRepository.findById(1L).get().getBalance());
        assertEquals(new BigDecimal("300.00"), accountRepository.findById(2L).get().getBalance());
    }

    @Test
    void findAllByFilter() {
        TransactionFilter filter = new TransactionFilter();
        filter.setBefore(new Date());
        filter.setOwner(userRepository.findById(1L).get());

        assertEquals(3, subj.getAllByFilter(filter).size());

    }
}