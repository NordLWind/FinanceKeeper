package ru.kostin.financekeeper.view.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kostin.financekeeper.dto.AccountDTO;
import ru.kostin.financekeeper.exception.BalanceException;
import ru.kostin.financekeeper.exception.ItemAlreadyExistsException;
import ru.kostin.financekeeper.exception.ItemNotExistException;
import ru.kostin.financekeeper.service.AccountService;
import ru.kostin.financekeeper.utils.ModParam;
import ru.kostin.financekeeper.view.web.form.account.AccountAddForm;
import ru.kostin.financekeeper.view.web.form.account.AccountDeleteForm;
import ru.kostin.financekeeper.view.web.form.account.AccountUpdateForm;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class AccountController extends AbstractController {
    private final AccountService accountService;

    @GetMapping("/account/add")
    public String addAccountGet(Model model) {
        model.addAttribute("form", new AccountAddForm());
        return "account-add";
    }

    @PostMapping("/account/add")
    public String addAccountPost(@Valid @ModelAttribute("form") AccountAddForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("form", form);
            return "account-add";
        }

        try {
            accountService.save(form.getName(), form.getBalance(), getUserId());
            return "redirect:/";
        } catch (ItemAlreadyExistsException e) {
            result.addError(new FieldError("form", "name", "Аккаунт с таким именем у Вас уже есть!"));
            model.addAttribute("form", form);
            return "account-add";
        } catch (BalanceException e) {
            result.addError(new FieldError("form", "balance", "Некорректный баланс!"));
            model.addAttribute("form", form);
            return "account-add";
        }
    }

    @GetMapping("/account/update")
    public String updAccountPost(Model model) {
        model.addAttribute("form", new AccountUpdateForm());
        model.addAttribute("accounts", getAccounts());
        model.addAttribute("param", Arrays.asList(ModParam.NAME, ModParam.BALANCE));
        return "account-update";
    }

    @PostMapping("/account/update")
    public String updAccountPost(@Valid @ModelAttribute("form") AccountUpdateForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("form", form);
            return "account-update";
        }

        try {
            ModParam param = ModParam.valueOf(form.getParam());
            accountService.update(getIdFromDTOList(accountService.getAll(getUserId()), form.getId()), param, form.getVal(), getUserId());
            return "redirect:/";
        } catch (ItemAlreadyExistsException e) {
            result.addError(new FieldError("form", "value", "Такой аккаунт уже есть!"));
            model.addAttribute("form", form);
            return "account-update";
        } catch (NumberFormatException e) {
            result.addError(new FieldError("form", "value", "Некорректный баланс!"));
            model.addAttribute("form", form);
            return "account-update";
        }
    }

    @GetMapping("/account/delete")
    public String delAccountGet(Model model) {
        model.addAttribute("form", new AccountDeleteForm());
        model.addAttribute("accounts", getAccounts());
        return "account-delete";
    }

    @PostMapping("/account/delete")
    public String delAccountPost(@Valid @ModelAttribute("form") AccountDeleteForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("form", form);
            return "account-delete";
        }

        try {
            accountService.delete(getIdFromDTOList(accountService.getAll(getUserId()), Integer.parseInt(form.getAccount().split("\\.")[0])));
            return "redirect:/";
        } catch (ItemNotExistException e) {
            result.addError(new FieldError("form", "account", "Нет такого аккаунта!"));
            model.addAttribute("form", form);
            return "account-delete";
        }
    }

    private List<String> getAccounts() {
        List<AccountDTO> accounts = accountService.getAll(getUserId());
        for (int i = 0; i < accounts.size(); i++) {
            accounts.get(i).setId(i + 1);
        }
        return accounts.stream()
                .map(a -> (a.getId() + ". " + a.getName() + ": " + a.getBalance()))
                .collect(Collectors.toList());
    }
}
