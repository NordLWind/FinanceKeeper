package ru.kostin.financekeeper.web.form.user;

import lombok.Data;

@Data
public class LoginForm {
    private String email;
    private String password;
}
