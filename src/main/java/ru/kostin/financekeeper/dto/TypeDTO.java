package ru.kostin.financekeeper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TypeDTO implements Dto {
    private long id;
    private String type;

    public Long getId() {
        return id;
    }
}
