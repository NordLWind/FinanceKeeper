package ru.kostin.financekeeper.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kostin.financekeeper.converter.AccountConverter;
import ru.kostin.financekeeper.entity.Account;
import ru.kostin.financekeeper.entity.User;
import ru.kostin.financekeeper.exception.ItemAlreadyExistsException;
import ru.kostin.financekeeper.exception.ItemNotExistException;
import ru.kostin.financekeeper.repository.AccountRepository;
import ru.kostin.financekeeper.repository.UserRepository;
import ru.kostin.financekeeper.utils.BalanceFormatter;
import ru.kostin.financekeeper.utils.BalanceFormatterImpl;
import ru.kostin.financekeeper.utils.ModParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static ru.kostin.financekeeper.service.TestUtils.getTestAccount;
import static ru.kostin.financekeeper.service.TestUtils.getTestUser;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AccountService.class, AccountConverter.class, BalanceFormatterImpl.class})
class AccountServiceTest {

    @Autowired
    AccountService subj;

    @MockBean
    AccountRepository accountRepository;

    @MockBean
    UserRepository userRepository;

    @SpyBean
    AccountConverter converter;

    @SpyBean
    BalanceFormatter formatter;

    @Test
    void get_ok() {
        Account account = getTestAccount();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(converter.convert(account)).thenCallRealMethod();

        assertEquals(account.getName(), subj.get(1L).getName());
    }

    @Test
    void get_notExist() {
        when(accountRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ItemNotExistException.class, () -> subj.get(2L));
    }

    @Test
    void getAll() {
        List<Account> accounts = new ArrayList<>();
        Account toAdd = getTestAccount();
        accounts.add(toAdd);
        when(accountRepository.findByOwner_Id(1L)).thenReturn(accounts);
        when(converter.convert(toAdd)).thenCallRealMethod();

        assertEquals("testAcc", subj.getAll(1L).get(0).getName());
    }

    @Test
    void add_ok() {
        User owner = getTestUser(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(owner));
        when(accountRepository.existsByNameAndOwner_Id("test", 1L)).thenReturn(false);
        when(formatter.format("213.00")).thenReturn(new BigDecimal("213.00"));

        subj.add("test", "213.00", 1L);

        verify(accountRepository).save(any());
    }

    @Test
    void add_alreadyExists() {
        when(accountRepository.existsByNameAndOwner_Id("exists", 1L)).thenReturn(true);
        assertThrows(ItemAlreadyExistsException.class, () -> subj.add("exists", "smth", 1L));
    }

    @Test
    void add_wrongBalance() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(getTestUser(true)));
        when(accountRepository.existsByNameAndOwner_Id("wrong", 1L)).thenReturn(false);
        doThrow(NumberFormatException.class).when(formatter).format("wrong_bal");

        assertThrows(NumberFormatException.class, () -> subj.add("wrong", "wrong_bal", 1L));
    }

    @Test
    void delete_ok() {
        when(accountRepository.existsById(1L)).thenReturn(true);
        subj.delete(1L);
        verify(accountRepository).deleteById(1L);
    }

    @Test
    void delete_notExists() {
        when(accountRepository.existsById(1L)).thenReturn(false);
        assertThrows(ItemNotExistException.class, () -> subj.delete(1L));
    }

    @Test
    void modify_ok() {
        Account toMod = getTestAccount();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(toMod));
        when(accountRepository.existsByNameAndOwner_Id("newName", 1L)).thenReturn(false);
        subj.modify(1L, ModParam.NAME, "newName", 1L);

        verify(accountRepository).save(toMod);
        assertEquals("newName", toMod.getName());

        subj.modify(1L, ModParam.BALANCE, "300.00", 1L);

        assertEquals(new BigDecimal("300.00"), toMod.getBalance());
    }

    @Test
    void modify_nameExists() {
        Account toMod = getTestAccount();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(toMod));
        when(accountRepository.existsByNameAndOwner_Id("newName", 1L)).thenReturn(true);

        assertThrows(ItemAlreadyExistsException.class, () -> subj.modify(1L, ModParam.NAME, "newName", 1L));
    }

    @Test
    void modify_wrongBalance() {
        Account toMod = getTestAccount();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(toMod));
        doThrow(NumberFormatException.class).when(formatter).format("wrongBal");

        assertThrows(NumberFormatException.class, () -> subj.modify(1L, ModParam.BALANCE, "wrongBal", 1L));
    }
}