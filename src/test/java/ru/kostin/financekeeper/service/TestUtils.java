package ru.kostin.financekeeper.service;

import ru.kostin.financekeeper.entity.Account;
import ru.kostin.financekeeper.entity.User;

import java.math.BigDecimal;

public class TestUtils {
    static User getTestUser(boolean correct) {
        User user = new User();
        user.setId(1L);
        user.setName(correct ? "test" : "wrongName");
        user.setEmail(correct ? "test@t" : "wrongEmail");
        user.setPassword(correct ? "password" : "wrongPassword");
        return user;
    }

    static Account getTestAccount() {
        Account account = new Account();
        account.setId(1L);
        account.setName("testAcc");
        account.setBalance(new BigDecimal("123.30"));
        account.setOwner(getTestUser(true));
        return account;
    }
}
