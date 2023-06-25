package ru.kostin.financekeeper.repository;

import lombok.RequiredArgsConstructor;
import ru.kostin.financekeeper.entity.Transaction;
import ru.kostin.financekeeper.utils.TransactionFilter;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class TransactionRepositoryCustomImpl implements TransactionRepositoryCustom {
    private final EntityManager em;

    @Override
    public List<Transaction> getAllByFilter(TransactionFilter filter) {
        Map<String, Object> params = new HashMap<>();
        String query = "select t from Transaction t where t.owner = :owner";
        params.put("owner", filter.getOwner());
        if (filter.getBefore() != null) {
            query += " and t.time < :dateBefore";
            params.put("dateBefore", filter.getBefore());
        }
        if (filter.getAfter() != null) {
            query += " and t.time > :dateAfter";
            params.put("dateAfter", filter.getAfter());
        }
        if (filter.getType() != null) {
            query += " and :types member of t.types";
            params.put("types", filter.getType());
        }
        TypedQuery<Transaction> typedQuery = em.createQuery(query, Transaction.class);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            typedQuery.setParameter(entry.getKey(), entry.getValue());
        }
        return typedQuery.getResultList();
    }
}
