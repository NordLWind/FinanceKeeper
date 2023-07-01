package ru.kostin.financekeeper.view.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kostin.financekeeper.exception.ItemAlreadyExistsException;
import ru.kostin.financekeeper.exception.ItemNotExistException;
import ru.kostin.financekeeper.service.TypeService;
import ru.kostin.financekeeper.view.web.form.type.TypeAddForm;
import ru.kostin.financekeeper.view.web.form.type.TypeDeleteForm;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class TypeController extends AbstractController {
    private final TypeService typeService;

    @GetMapping("/type/list")
    public String getAll(Model model) {
        model.addAttribute("options", getTypesForPrint(typeService));
        return "list-template";
    }

    @GetMapping("/type/add")
    public String getTypeAdd(Model model) {
        model.addAttribute("form", new TypeAddForm());
        return "type-add";
    }

    @PostMapping("/type/add")
    public String postTypeAdd(@Valid @ModelAttribute("form") TypeAddForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("form", form);
            return "type-add";
        }
        try {
            typeService.save(form.getType());
            return "redirect:/type/menu";
        } catch (ItemAlreadyExistsException e) {
            result.addError(new FieldError("form", "type", "Такой тип уже есть"));
            model.addAttribute("form", form);
            return "type-add";
        }
    }

    @GetMapping("/type/delete")
    public String getTypeDel(Model model) {
        model.addAttribute("form", new TypeDeleteForm());
        model.addAttribute("options", getTypesForPrint(typeService));
        return "type-delete";
    }

    @PostMapping("/type/delete")
    public String postTypeDel(@Valid @ModelAttribute("form") TypeDeleteForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("form", form);
            return "type-delete";
        }
        try {
            typeService.delete(getIdFromDTOList(typeService.getAll(), Integer.parseInt(form.getType().split("\\.")[0])));
            return "redirect:/type/menu";
        } catch (ItemNotExistException e) {
            result.addError(new FieldError("form", "type", "Тут этого быть не должно..."));
            model.addAttribute("form");
            return "type-delete";
        }
    }
}
