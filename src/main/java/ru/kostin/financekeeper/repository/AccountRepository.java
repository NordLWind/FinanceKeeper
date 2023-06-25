package ru.kostin.financekeeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kostin.financekeeper.entity.Account;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByNameAndOwner_Id(String name, Long id);

    List<Account> findByOwner_Id(Long id);
}
