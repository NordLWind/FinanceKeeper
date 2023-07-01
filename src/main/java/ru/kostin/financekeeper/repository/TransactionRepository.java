package ru.kostin.financekeeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kostin.financekeeper.entity.Transaction;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long>, TransactionRepositoryCustom {
    List<Transaction> findByOwner_Id(Long id);

}
