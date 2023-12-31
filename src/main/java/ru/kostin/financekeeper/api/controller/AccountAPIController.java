package ru.kostin.financekeeper.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kostin.financekeeper.api.json.CompletionResponse;
import ru.kostin.financekeeper.api.json.account.AccountAddRequest;
import ru.kostin.financekeeper.api.json.account.AccountDeleteRequest;
import ru.kostin.financekeeper.api.json.account.AccountListResponse;
import ru.kostin.financekeeper.api.json.account.AccountUpdateRequest;
import ru.kostin.financekeeper.dto.AccountDTO;
import ru.kostin.financekeeper.exception.ItemAlreadyExistsException;
import ru.kostin.financekeeper.exception.ItemNotExistException;
import ru.kostin.financekeeper.service.AccountService;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountAPIController extends AbstractAPIController {
    private final AccountService accountService;

    @GetMapping("/list")
    public ResponseEntity<AccountListResponse> list() {
        Long userId = getUserId();
        List<AccountDTO> accounts = accountService.getAll(userId);
        for (int i = 0; i < accounts.size(); i++) {
            accounts.get(i).setId(i + 1);
        }
        return ok(new AccountListResponse(accounts));
    }

    @PostMapping("/add")
    public ResponseEntity<CompletionResponse> add(@RequestBody AccountAddRequest accountAddReq) {
        Long userId = getUserId();
        try {
            accountService.save(accountAddReq.getName(), accountAddReq.getBalance(), userId);
            return ok(new CompletionResponse(true));
        } catch (ItemAlreadyExistsException | NumberFormatException e) {
            return status(HttpStatus.BAD_REQUEST).body(new CompletionResponse(false));
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<CompletionResponse> delete(@RequestBody AccountDeleteRequest accountDeleteReq) {
        Long userId = getUserId();
        long idToDelete = getIdFromDTOList(accountService.getAll(userId), accountDeleteReq.getId());
        try {
            accountService.delete(idToDelete);
            return ok(new CompletionResponse(true));
        } catch (ItemNotExistException e) {
            return status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/update")
    public ResponseEntity<CompletionResponse> update(@RequestBody AccountUpdateRequest accountUpdateReq) {
        Long userId = getUserId();
        Long accIdToUpdate = getIdFromDTOList(accountService.getAll(userId), accountUpdateReq.getId());
        try {
            accountService.update(accIdToUpdate, accountUpdateReq.getParam(), accountUpdateReq.getVal(), userId);
            return ok(new CompletionResponse(true));
        } catch (ItemNotExistException | ItemAlreadyExistsException | NumberFormatException e) {
            return status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
