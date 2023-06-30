package ru.kostin.financekeeper.view.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    @GetMapping("/")
    public String getMainMenu() {
        return "index";
    }
}
