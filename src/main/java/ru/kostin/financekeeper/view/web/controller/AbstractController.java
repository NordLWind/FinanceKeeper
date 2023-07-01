package ru.kostin.financekeeper.view.web.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kostin.financekeeper.dto.AccountDTO;
import ru.kostin.financekeeper.dto.Dto;
import ru.kostin.financekeeper.dto.TypeDTO;
import ru.kostin.financekeeper.security.CustomUserDetails;
import ru.kostin.financekeeper.service.AccountService;
import ru.kostin.financekeeper.service.TypeService;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractController {
    protected long getUserId() {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            return user.getId();
        }
        throw new UsernameNotFoundException("You shouldn`t see this...");
    }

    protected Long getIdFromDTOList(List<? extends Dto> list, int id) {
        return list.get(id - 1).getId();
    }

    protected List<String> getAccountsForPrint(AccountService accountService) {
        List<AccountDTO> accounts = accountService.getAll(getUserId());
        for (int i = 0; i < accounts.size(); i++) {
            accounts.get(i).setId(i + 1);
        }
        return accounts.stream()
                .map(a -> (a.getId() + ". " + a.getName() + ": " + a.getBalance()))
                .collect(Collectors.toList());
    }

    protected List<String> getTypesForPrint(TypeService typeService) {
        List<TypeDTO> data = typeService.getAll();
        for (int i = 0; i < data.size(); i++) {
            data.get(i).setId(i + 1);
        }
        return data.stream().map(t -> t.getId() + ". " + t.getType()).collect(Collectors.toList());
    }
}
