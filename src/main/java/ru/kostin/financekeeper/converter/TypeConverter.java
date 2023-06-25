package ru.kostin.financekeeper.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.kostin.financekeeper.dto.TypeDTO;
import ru.kostin.financekeeper.entity.Type;

@Service
public class TypeConverter implements Converter<Type, TypeDTO> {
    @Override
    public TypeDTO convert(Type source) {
        TypeDTO dto = new TypeDTO();
        dto.setId(source.getId());
        dto.setType(source.getType());
        return dto;
    }
}
