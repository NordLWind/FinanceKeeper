package ru.kostin.financekeeper.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kostin.financekeeper.converter.UserConverter;
import ru.kostin.financekeeper.dto.UserDTO;
import ru.kostin.financekeeper.entity.User;
import ru.kostin.financekeeper.exception.ItemAlreadyExistsException;
import ru.kostin.financekeeper.exception.ItemNotExistException;
import ru.kostin.financekeeper.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static ru.kostin.financekeeper.service.TestUtils.getTestUser;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {UserService.class, UserConverter.class})
class UserServiceTest {

    @Autowired
    UserService subj;

    @SpyBean
    Converter<User, UserDTO> converter;

    @MockBean
    UserRepository userRepository;

    @MockBean
    PasswordEncoder encoder;

    @Test
    void get() {
        User test = getTestUser(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(test));
        when(converter.convert(test)).thenCallRealMethod();
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertEquals("test", subj.get(1L).getName());
        assertThrows(ItemNotExistException.class, () -> subj.get(2L));
    }

    @Test
    void add() {
        when(userRepository.existsByEmail("newEmail")).thenReturn(false);
        subj.save("newName", "newEmail", "password");
        verify(userRepository).save(any());
        when(userRepository.existsByEmail("existingEmail")).thenReturn(true);
        assertThrows(ItemAlreadyExistsException.class, () -> subj.save("name", "existingEmail", "password"));

    }

    @Test
    void auth() {
        User test = getTestUser(true);
        when(userRepository.findByEmail("test@t")).thenReturn(Optional.of(test));
        when(encoder.matches("rawPassword", test.getPassword())).thenReturn(true);
        when(converter.convert(test)).thenCallRealMethod();

        assertEquals("test", subj.auth("test@t", "rawPassword").getName());

        test = getTestUser(false);
        when(userRepository.findByEmail("wrongEmail")).thenReturn(Optional.of(getTestUser(false)));
        when(encoder.matches("rawWrongPassword", test.getPassword())).thenReturn(false);

        assertThrows(ItemNotExistException.class, () -> subj.auth("wrongEmail", "rawWrongPassword"));
    }
}