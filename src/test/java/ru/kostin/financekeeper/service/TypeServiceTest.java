package ru.kostin.financekeeper.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kostin.financekeeper.dto.TypeDTO;
import ru.kostin.financekeeper.entity.Type;
import ru.kostin.financekeeper.exception.ItemAlreadyExistsException;
import ru.kostin.financekeeper.exception.ItemNotExistException;
import ru.kostin.financekeeper.repository.TypeRepository;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.kostin.financekeeper.service.TestUtils.getRandomTestType;
import static ru.kostin.financekeeper.service.TestUtils.getTestTypeDTO;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TypeServiceTest {

    @Autowired
    private TypeService subj;

    @MockBean
    private TypeRepository typeRepository;

    @SpyBean
    private Converter<Type, TypeDTO> converter;


    @Test
    public void get() {
        Type test = getRandomTestType(true);
        when(typeRepository.findById(test.getId())).thenReturn(of(test));
        when(typeRepository.findById(100L)).thenReturn(empty());
        when(converter.convert(test)).thenReturn(getTestTypeDTO(test));

        assertEquals(getTestTypeDTO(test), subj.get(test.getId()));
        assertThrows(ItemNotExistException.class, () -> subj.get(100));
    }

    @org.junit.Test
    public void getAll() {
        List<Type> test = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Type testT = getRandomTestType(true);
            test.add(testT);
            when(converter.convert(testT)).thenReturn(getTestTypeDTO(testT));
        }
        when(typeRepository.findAll()).thenReturn(test);

        assertEquals(10, subj.getAll().size());
    }

    @org.junit.Test
    public void save() {
        Type test = getRandomTestType(false);
        when(typeRepository.existsByType(test.getType())).thenReturn(false);

        subj.save(test.getType());

        verify(typeRepository).save(any());
    }

    @org.junit.Test
    public void update() {
        Type test = getRandomTestType(true);
        when(typeRepository.findById(test.getId())).thenReturn(of(test));
        when(typeRepository.existsByType("newType")).thenReturn(false);
        when(typeRepository.findById(100L)).thenReturn(empty());
        when(typeRepository.existsByType("exists")).thenReturn(true);

        subj.update(test.getId(), "newType");
        verify(typeRepository).updateTypeById("newType", test.getId());

        assertThrows(ItemNotExistException.class, () -> subj.update(100, "e"));
        assertThrows(ItemAlreadyExistsException.class, () -> subj.update(test.getId(), "exists"));
    }

    @Test
    public void delete() {
        Type test = getRandomTestType(true);
        when(typeRepository.findById(test.getId())).thenReturn(of(test));
        when(typeRepository.findById(100L)).thenReturn(empty());

        subj.delete(test.getId());
        verify(typeRepository).delete(test);

        assertThrows(ItemNotExistException.class, () -> subj.delete(100));
    }
}