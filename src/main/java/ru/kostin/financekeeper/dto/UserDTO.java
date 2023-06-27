package ru.kostin.financekeeper.dto;

import lombok.Data;

@Data
public class UserDTO implements Dto {
    private long id;
    private String name;
    private String email;

    public Long getId() {
        return id;
    }
}
