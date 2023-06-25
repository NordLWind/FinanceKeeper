package ru.kostin.financekeeper.service;

import ru.kostin.financekeeper.dto.TypeDTO;
import ru.kostin.financekeeper.entity.Account;
import ru.kostin.financekeeper.entity.Transaction;
import ru.kostin.financekeeper.entity.Type;
import ru.kostin.financekeeper.entity.User;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    static Type getRandomTestType(boolean withId) {
        Type type = new Type();
        type.setType(UUID.randomUUID().toString());
        if (withId) {
            type.setId((long) (Math.random() * 100 + 1));
        }
        return type;
    }

    static TypeDTO getTestTypeDTO(Type type) {
        TypeDTO dto = new TypeDTO();
        dto.setId(type.getId());
        dto.setType(type.getType());
        return dto;
    }

    static Date getDate(String date) throws ParseException {
        if (date == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return new Timestamp(format.parse(date).getTime());
    }

    static List<Transaction> getList() {
        List<Transaction> result = new ArrayList<>();
        result.add(new Transaction());
        result.add(new Transaction());
        return result;
    }
}
