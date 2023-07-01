package ru.kostin.financekeeper.view.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kostin.financekeeper.dto.TransactionDTO;
import ru.kostin.financekeeper.exception.BalanceException;
import ru.kostin.financekeeper.exception.ItemNotExistException;
import ru.kostin.financekeeper.service.AccountService;
import ru.kostin.financekeeper.service.TransactionService;
import ru.kostin.financekeeper.service.TypeService;
import ru.kostin.financekeeper.view.web.form.transaction.ReportForm;
import ru.kostin.financekeeper.view.web.form.transaction.TransactionAddForm;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TransactionController extends AbstractController {
    private final TransactionService transactionService;
    private final AccountService accountService;
    private final TypeService typeService;

    @GetMapping("/report")
    public String getReport(Model model) {
        model.addAttribute("form", new ReportForm());
        return "report";
    }

    @PostMapping("/transaction/report")
    public String report(@Valid @ModelAttribute("form") ReportForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("form", form);
            return "report";
        }

        try {
            List<TransactionDTO> data = transactionService.getReport(form.getAfter(), form.getBefore(), 0, getUserId());
            for (int i = 0; i < data.size(); i++) {
                data.get(i).setId(i + 1);
            }

            model.addAttribute("options", data);
            return "list-template";

        } catch (ParseException | ItemNotExistException e) {
            result.addError(new FieldError("form", "after", "smthWrong!"));
            model.addAttribute("form", form);
            return "report";
        }
    }

    @GetMapping("/transaction/add")
    public String getTransactionAdd(Model model) {
        model.addAttribute("form", new TransactionAddForm());
        model.addAttribute("accounts", getAccountsForPrint(accountService));
        model.addAttribute("types", getTypesForPrint(typeService));
        return "transaction-add";
    }

    @PostMapping("/transaction/add")
    public String postTransactionAdd(@Valid @ModelAttribute("form") TransactionAddForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("form", form);
            return "transaction-add";
        }

        try {
            transactionService.save(
                    getIdFromDTOList(accountService.getAll(getUserId()), Integer.parseInt(form.getSource().split("\\.")[0])),
                    getIdFromDTOList(accountService.getAll(getUserId()), Integer.parseInt(form.getTarget().split("\\.")[0])),
                    form.getAmount(),
                    form.getDescription(),
                    "0",
                    getUserId(),
                    Collections.singletonList(getIdFromDTOList(typeService.getAll(), Integer.parseInt(form.getType().split("\\.")[0]))));
        } catch (ParseException | BalanceException e) {
            result.addError(new FieldError("form", "amount", "Слишком много!"));
            model.addAttribute("form", form);
            return "transaction-add";
        }
        return "redirect:/";
    }
}
