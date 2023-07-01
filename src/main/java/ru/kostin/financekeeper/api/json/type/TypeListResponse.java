package ru.kostin.financekeeper.api.json.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kostin.financekeeper.dto.TypeDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeListResponse {
    private List<TypeDTO> types;
}
