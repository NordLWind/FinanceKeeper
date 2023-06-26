package ru.kostin.financekeeper.view.api.json.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kostin.financekeeper.dto.TransactionDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionReportResponse {
    private List<TransactionDTO> data;
}
