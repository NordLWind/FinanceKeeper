package ru.kostin.financekeeper.utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public interface BalanceFormatter {
    BigDecimal format(String rawBalance);
}
