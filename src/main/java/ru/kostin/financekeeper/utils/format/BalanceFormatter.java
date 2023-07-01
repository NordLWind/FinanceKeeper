package ru.kostin.financekeeper.utils.format;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public interface BalanceFormatter {
    BigDecimal format(String rawBalance);
}
