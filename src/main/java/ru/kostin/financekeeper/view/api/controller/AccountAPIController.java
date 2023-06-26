package ru.kostin.financekeeper.view.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kostin.financekeeper.dto.AccountDTO;
import ru.kostin.financekeeper.exception.ItemAlreadyExistsException;
import ru.kostin.financekeeper.exception.ItemNotExistException;
import ru.kostin.financekeeper.service.AccountService;
import ru.kostin.financekeeper.view.api.json.CompletionResponse;
import ru.kostin.financekeeper.view.api.json.account.AccountAddRequest;
import ru.kostin.financekeeper.view.api.json.account.AccountDeleteRequest;
import ru.kostin.financekeeper.view.api.json.account.AccountListResponse;
import ru.kostin.financekeeper.view.api.json.account.AccountUpdateRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountAPIController extends AbstractAPIController {
    private final AccountService accountService;

    @GetMapping("/list")
    public ResponseEntity<AccountListResponse> list(HttpServletRequest request) {
        Long id = getIdFromReqSession(request);
        if (id == null) {
            return status(HttpStatus.UNAUTHORIZED).build();
        }
        List<AccountDTO> accounts = accountService.getAll(id);
        for (int i = 0; i < accounts.size(); i++) {
            accounts.get(i).setId(i + 1);
        }
        return ok(new AccountListResponse(accounts));
    }

    @PostMapping("/add")
    public ResponseEntity<CompletionResponse> add(@RequestBody AccountAddRequest accountAddReq, HttpServletRequest servletReq) {
        Long id = getIdFromReqSession(servletReq);
        if (id == null) {
            return status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            accountService.add(accountAddReq.getName(), accountAddReq.getBalance(), id);
            return ok(new CompletionResponse(true));
        } catch (ItemAlreadyExistsException | NumberFormatException e) {
            return status(HttpStatus.BAD_REQUEST).body(new CompletionResponse(false));
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<CompletionResponse> delete(@RequestBody AccountDeleteRequest accountDeleteReq, HttpServletRequest servletReq) {
        Long id = getIdFromReqSession(servletReq);
        if (id == null) {
            return status(HttpStatus.UNAUTHORIZED).build();
        }

        long idToDelete = getIdFromDTOList(accountService.getAll(id), accountDeleteReq.getId());
        try {
            accountService.delete(idToDelete);
            return ok(new CompletionResponse(true));
        } catch (ItemNotExistException e) {
            return status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/update")
    public ResponseEntity<CompletionResponse> update(@RequestBody AccountUpdateRequest accountUpdateReq, HttpServletRequest servletReq) {
        Long id = getIdFromReqSession(servletReq);
        if (id == null) {
            return status(HttpStatus.UNAUTHORIZED).build();
        }
        Long accIdToUpdate = getIdFromDTOList(accountService.getAll(id), accountUpdateReq.getId());
        try {
            accountService.update(accIdToUpdate, accountUpdateReq.getParam(), accountUpdateReq.getVal(), id);
            return ok(new CompletionResponse(true));
        } catch (ItemNotExistException | ItemAlreadyExistsException | NumberFormatException e) {
            return status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
