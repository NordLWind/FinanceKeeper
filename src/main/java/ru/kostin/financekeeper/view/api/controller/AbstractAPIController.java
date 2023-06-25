package ru.kostin.financekeeper.view.api.controller;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractAPIController {
    protected void putIdToReqSession(Long id, HttpServletRequest request) {
        request.getSession().setAttribute("userId", id);
    }

    protected Long getIdFromReqSession(HttpServletRequest request) {
        return (Long) request.getSession().getAttribute("userId");
    }
}
