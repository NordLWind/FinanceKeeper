package ru.kostin.financekeeper.api.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import ru.kostin.financekeeper.dto.Dto;
import ru.kostin.financekeeper.security.CustomUserDetails;

import java.util.List;
import java.util.Optional;

public abstract class AbstractAPIController {

    protected Long getUserId() {
        return Optional.ofNullable(
                        (CustomUserDetails) SecurityContextHolder.getContext()
                                .getAuthentication()
                                .getPrincipal())
                .map(CustomUserDetails::getId).orElse(null);
    }

    protected Long getIdFromDTOList(List<? extends Dto> list, int id) {
        return list.get(id - 1).getId();
    }
}
