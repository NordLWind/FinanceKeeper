package ru.kostin.financekeeper.view.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kostin.financekeeper.exception.ItemAlreadyExistsException;
import ru.kostin.financekeeper.service.UserService;
import ru.kostin.financekeeper.view.web.form.LoginForm;
import ru.kostin.financekeeper.view.web.form.UserAddForm;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("form", new LoginForm());
        return "login";
    }

    @GetMapping("/register")
    public String getUserAdd(Model model) {
        model.addAttribute("form", new UserAddForm());
        return "register";
    }

    @PostMapping("/register")
    public String postUserAdd(@ModelAttribute @Valid UserAddForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("form", form);
            return "register";
        }

        try {
            userService.save(form.getName(), form.getEmail(), form.getPassword());
            return "redirect:/";
        } catch (ItemAlreadyExistsException e) {
            result.addError(new FieldError("form", "email", "Такой адрес уже зарегистрирован!"));
            model.addAttribute("form", form);
            return "register";
        }
    }
}
