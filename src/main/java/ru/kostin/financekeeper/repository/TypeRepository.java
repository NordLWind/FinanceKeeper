package ru.kostin.financekeeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kostin.financekeeper.entity.Type;

public interface TypeRepository extends JpaRepository<Type, Long> {

    void updateTypeById(String val, long id);

    boolean existsByType(String type);
}
