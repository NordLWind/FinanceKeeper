package ru.kostin.financekeeper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kostin.financekeeper.dto.UserDTO;
import ru.kostin.financekeeper.entity.User;
import ru.kostin.financekeeper.exception.ItemAlreadyExistsException;
import ru.kostin.financekeeper.exception.ItemNotExistException;
import ru.kostin.financekeeper.repository.UserRepository;
import ru.kostin.financekeeper.security.Role;

import java.util.Collections;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final Converter<User, UserDTO> converter;
    private final PasswordEncoder encoder;

    public UserDTO get(Long id) {
        return converter.convert(userRepo.findById(id).orElseThrow(ItemNotExistException::new));
    }

    public void add(String name, String email, String password, Set<Role> roles) {
        if (userRepo.existsByEmail(email)) {
            throw new ItemAlreadyExistsException();
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        user.setRoles(roles);
        userRepo.save(user);
    }

    public void add(String name, String email, String password) {
        add(name, email, password, Collections.singleton(Role.USER));
    }

    public UserDTO auth(String email, String password) {
        User user = userRepo.findByEmail(email).orElseThrow(ItemNotExistException::new);
        if (encoder.matches(password, user.getPassword())) {
            return converter.convert(user);
        }
        throw new ItemNotExistException();
    }
}
