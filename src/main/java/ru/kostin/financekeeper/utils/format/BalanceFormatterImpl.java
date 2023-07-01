package ru.kostin.financekeeper.utils.format;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class BalanceFormatterImpl implements BalanceFormatter {
    public BigDecimal format(String rawBalance) {
        BigDecimal balance = new BigDecimal(rawBalance).setScale(2, RoundingMode.HALF_EVEN);
        if (balance.signum() == -1) {
            throw new NumberFormatException();
        }
        return balance;
    }
}
