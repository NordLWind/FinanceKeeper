package ru.kostin.financekeeper.api.json.user;

import lombok.Data;

@Data
public class AddUserRequest {
    private String name;
    private String email;
    private String password;
}
