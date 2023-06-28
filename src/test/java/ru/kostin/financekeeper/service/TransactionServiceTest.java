package ru.kostin.financekeeper.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.test.context.ContextConfiguration;
import ru.kostin.financekeeper.converter.TransactionConverter;
import ru.kostin.financekeeper.dto.TransactionDTO;
import ru.kostin.financekeeper.entity.Account;
import ru.kostin.financekeeper.entity.Transaction;
import ru.kostin.financekeeper.entity.Type;
import ru.kostin.financekeeper.entity.User;
import ru.kostin.financekeeper.exception.BalanceException;
import ru.kostin.financekeeper.exception.SameAccountsException;
import ru.kostin.financekeeper.repository.AccountRepository;
import ru.kostin.financekeeper.repository.TransactionRepository;
import ru.kostin.financekeeper.repository.TypeRepository;
import ru.kostin.financekeeper.repository.UserRepository;
import ru.kostin.financekeeper.utils.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ru.kostin.financekeeper.service.TestUtils.*;

@SpringBootTest
@ContextConfiguration(classes = {TransactionService.class, TransactionConverter.class, BalanceFormatterImpl.class, DateFormatterImpl.class})
public class TransactionServiceTest {

    @Autowired
    TransactionService subj;

    @MockBean
    UserRepository userRepository;

    @MockBean
    AccountRepository accountRepository;

    @MockBean
    TypeRepository typeRepository;

    @MockBean
    TransactionRepository transactionRepository;

    @MockBean
    Converter<Transaction, TransactionDTO> converter;

    @SpyBean
    BalanceFormatter balanceFormatter;

    @SpyBean
    DateFormatter dateFormatter;

    @Test
    void getReport() throws ParseException {
        User owner = getTestUser(true);
        TransactionFilter filter = new TransactionFilter();
        Type filterType = getRandomTestType(true);
        filter.setType(filterType);
        filter.setOwner(owner);
        filter.setBefore(getDate("2023-12-31 10:00"));
        filter.setAfter(getDate("2022-12-31 10:00"));
        when(typeRepository.findById(filterType.getId())).thenReturn(Optional.of(filterType));
        when(userRepository.findById(1L)).thenReturn(Optional.of(owner));
        when(transactionRepository.getAllByFilter(any())).thenReturn(getList());
        when(converter.convert(any())).thenReturn(new TransactionDTO());

        ArgumentCaptor<TransactionFilter> argumentCaptor = ArgumentCaptor.forClass(TransactionFilter.class);
        subj.getReport("2022-12-31 10:00", "2023-12-31 10:00", filterType.getId(), 1L);
        verify(transactionRepository).getAllByFilter(argumentCaptor.capture());
        assertEquals(filter, argumentCaptor.getValue());

    }

    @Test
    void add() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(getTestAccount()));
        Account target = getTestAccount();
        target.setId(2L);
        when(accountRepository.findById(2L)).thenReturn(Optional.of(target));
        when(userRepository.findById(1L)).thenReturn(Optional.of(getTestUser(true)));
        assertThrows(BalanceException.class, () -> subj.save(1L, 2L, "200", "desc", "0", 1L, new ArrayList<>()));
        assertThrows(ParseException.class, () -> subj.save(1L, 2L, "10", "desc", "not-a-date", 1L, new ArrayList<>()));
        assertThrows(SameAccountsException.class, () -> subj.save(1L, 1L, "12", "desc", "0", 1L, new ArrayList<>()));
    }
}