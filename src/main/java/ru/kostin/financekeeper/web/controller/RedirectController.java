package ru.kostin.financekeeper.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    @GetMapping("/")
    public String getMainMenu() {
        return "index";
    }

    @GetMapping("/index")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/account/menu")
    public String getAccountMenu() {
        return "account-menu";
    }

    @GetMapping("/type/menu")
    public String getTypeMenu() {
        return "type-menu";
    }
}
