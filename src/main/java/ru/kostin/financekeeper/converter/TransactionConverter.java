package ru.kostin.financekeeper.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.kostin.financekeeper.dto.TransactionDTO;
import ru.kostin.financekeeper.entity.Transaction;

@Service
public class TransactionConverter implements Converter<Transaction, TransactionDTO> {
    @Override
    public TransactionDTO convert(Transaction source) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(source.getId());
        dto.setSource(source.getSource().getName());
        dto.setTarget(source.getTarget().getName());
        dto.setAmount(source.getAmount().toString());
        dto.setDescription(source.getDescription());
        dto.setDate(source.getTime().toString());
        return dto;
    }
}
