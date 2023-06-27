package ru.kostin.financekeeper.view.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kostin.financekeeper.dto.AccountDTO;
import ru.kostin.financekeeper.dto.TransactionDTO;
import ru.kostin.financekeeper.dto.TypeDTO;
import ru.kostin.financekeeper.service.AccountService;
import ru.kostin.financekeeper.service.TransactionService;
import ru.kostin.financekeeper.service.TypeService;
import ru.kostin.financekeeper.view.api.json.CompletionResponse;
import ru.kostin.financekeeper.view.api.json.transaction.TransactionAddRequest;
import ru.kostin.financekeeper.view.api.json.transaction.TransactionReportRequest;
import ru.kostin.financekeeper.view.api.json.transaction.TransactionReportResponse;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionAPIController extends AbstractAPIController {
    private final TransactionService transactionService;
    private final AccountService accountService;
    private final TypeService typeService;

    @PostMapping("/add")
    public ResponseEntity<CompletionResponse> add(@RequestBody TransactionAddRequest transactionAddReq) {
        Long userId = getIdFromReqSession();
        List<AccountDTO> accounts = accountService.getAll(userId);
        List<TypeDTO> types = typeService.getAll();
        try {
            transactionService.add(
                    getIdFromDTOList(accounts, transactionAddReq.getIdSource()),
                    getIdFromDTOList(accounts, transactionAddReq.getIdTarget()),
                    transactionAddReq.getAmount(),
                    transactionAddReq.getDescription(),
                    transactionAddReq.getDate(),
                    userId,
                    Collections.singletonList(getIdFromDTOList(types, transactionAddReq.getType()))
            );
            return ok(new CompletionResponse(true));
        } catch (ParseException | NumberFormatException e) {
            return status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/report")
    public ResponseEntity<TransactionReportResponse> getReport(@RequestBody TransactionReportRequest transactionReportReq) {
        Long userId = getIdFromReqSession();
        try {
            List<TransactionDTO> data = transactionService.getReport(
                    transactionReportReq.getDAfter(),
                    transactionReportReq.getDBefore(),
                    getIdFromDTOList(typeService.getAll(), transactionReportReq.getType()),
                    userId
            );

            return ok(new TransactionReportResponse(data));
        } catch (ParseException | NumberFormatException e) {
            return status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
