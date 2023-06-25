package ru.kostin.financekeeper.utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class BalanceFormatterImpl implements BalanceFormatter {
    public BigDecimal format(String rawBalance) {
        return new BigDecimal(rawBalance).setScale(2, RoundingMode.HALF_EVEN);
    }
}
