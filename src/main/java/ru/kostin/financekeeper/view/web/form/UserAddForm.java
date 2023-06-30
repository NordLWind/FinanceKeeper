package ru.kostin.financekeeper.view.web.form;

import lombok.Data;

@Data
public class UserAddForm {
    private String name;
    private String email;
    private String password;
}
