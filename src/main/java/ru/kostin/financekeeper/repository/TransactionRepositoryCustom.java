package ru.kostin.financekeeper.repository;

import ru.kostin.financekeeper.entity.Transaction;
import ru.kostin.financekeeper.utils.TransactionFilter;

import java.util.List;

public interface TransactionRepositoryCustom {
    List<Transaction> getAllByFilter(TransactionFilter filter);
}
