package ru.kostin.financekeeper.view.web.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kostin.financekeeper.dto.Dto;
import ru.kostin.financekeeper.security.CustomUserDetails;

import java.util.List;

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
}
