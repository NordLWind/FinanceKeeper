package ru.kostin.financekeeper.api.json.user;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
