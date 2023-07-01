package ru.kostin.financekeeper.view.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    @GetMapping("/")
    public String getMainMenu() {
        return "index";
    }

    @GetMapping("/account/menu")
    public String getAccountMenu() {
        return "account-menu";
    }
}
