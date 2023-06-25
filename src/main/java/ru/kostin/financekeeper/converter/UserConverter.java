package ru.kostin.financekeeper.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.kostin.financekeeper.dto.UserDTO;
import ru.kostin.financekeeper.entity.User;

@Service
public class UserConverter implements Converter<User, UserDTO> {
    @Override
    public UserDTO convert(User source) {
        UserDTO target = new UserDTO();
        target.setId(source.getId());
        target.setName(source.getName());
        target.setEmail(source.getEmail());
        return target;
    }
}
