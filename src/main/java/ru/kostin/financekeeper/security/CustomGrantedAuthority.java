package ru.kostin.financekeeper.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public class CustomGrantedAuthority implements GrantedAuthority {
    private final String PREFIX = "ROLE_";
    private final Role role;

    @Override
    public String getAuthority() {
        return PREFIX + role.name();
    }
}
