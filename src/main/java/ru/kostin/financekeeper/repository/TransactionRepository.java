package ru.kostin.financekeeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kostin.financekeeper.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>, TransactionRepositoryCustom {

}
