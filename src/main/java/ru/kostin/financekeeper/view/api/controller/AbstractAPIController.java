package ru.kostin.financekeeper.view.api.controller;

import ru.kostin.financekeeper.dto.Dto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public abstract class AbstractAPIController {
    protected void putIdToReqSession(Long id, HttpServletRequest request) {
        request.getSession().setAttribute("userId", id);
    }

    protected Long getIdFromReqSession(HttpServletRequest request) {
        return (Long) request.getSession().getAttribute("userId");
    }

    protected Long getIdFromDTOList(List<? extends Dto> list, int id) {
        return list.get(id - 1).getId();
    }
}
