package ru.kostin.financekeeper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.kostin.financekeeper.dto.TypeDTO;
import ru.kostin.financekeeper.entity.Type;
import ru.kostin.financekeeper.exception.ItemAlreadyExistsException;
import ru.kostin.financekeeper.exception.ItemNotExistException;
import ru.kostin.financekeeper.repository.TypeRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TypeService {
    private final TypeRepository typeRepository;
    private final Converter<Type, TypeDTO> converter;

    public TypeDTO get(long id) {
        return converter.convert(typeRepository.findById(id).orElseThrow(ItemNotExistException::new));
    }

    public List<TypeDTO> getAll() {
        return typeRepository.findAll().stream().sorted(Comparator.comparingLong(Type::getId)).map(converter::convert).collect(Collectors.toList());
    }

    public void save(String type) {
        checkIfExists(type);
        Type toAdd = new Type();
        toAdd.setType(type);
        typeRepository.save(toAdd);
    }

    public void update(long id, String val) {
        typeRepository.findById(id).orElseThrow(ItemNotExistException::new);
        checkIfExists(val);
        typeRepository.updateTypeById(val, id);
    }

    public void delete(long id) {
        Type toDelete = typeRepository.findById(id).orElseThrow(ItemNotExistException::new);
        typeRepository.delete(toDelete);
    }

    private void checkIfExists(String type) throws ItemAlreadyExistsException {
        if (typeRepository.existsByType(type)) {
            throw new ItemAlreadyExistsException();
        }
    }
}
