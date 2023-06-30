package ru.kostin.financekeeper.view.web.form.user;

import lombok.Data;

@Data
public class UserAddForm {
    private String name;
    private String email;
    private String password;
}
