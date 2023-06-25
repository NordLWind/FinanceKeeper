package ru.kostin.financekeeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.kostin.financekeeper.entity.Type;

public interface TypeRepository extends JpaRepository<Type, Long> {
    @Transactional
    @Modifying
    @Query("update Type t set t.type = ?1 where t.id = ?2")
    void updateTypeById(String type, Long id);

    boolean existsByType(String type);
}
