package ru.kostin.financekeeper.view.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kostin.financekeeper.dto.TypeDTO;
import ru.kostin.financekeeper.exception.ItemAlreadyExistsException;
import ru.kostin.financekeeper.exception.ItemNotExistException;
import ru.kostin.financekeeper.service.TypeService;
import ru.kostin.financekeeper.view.api.json.CompletionResponse;
import ru.kostin.financekeeper.view.api.json.type.TypeAddRequest;
import ru.kostin.financekeeper.view.api.json.type.TypeDeleteRequest;
import ru.kostin.financekeeper.view.api.json.type.TypeListResponse;
import ru.kostin.financekeeper.view.api.json.type.TypeUpdateRequest;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/type")
@RequiredArgsConstructor
public class TypeAPIController extends AbstractAPIController {
    private final TypeService typeService;

    @GetMapping("/list")
    public ResponseEntity<TypeListResponse> getTypes() {
        List<TypeDTO> types = typeService.getAll();
        for (int i = 0; i < types.size(); i++) {
            types.get(i).setId(i + 1);
        }
        return ok(new TypeListResponse(types));
    }

    @PostMapping("/add")
    public ResponseEntity<CompletionResponse> add(@RequestBody TypeAddRequest typeAddReq) {
        try {
            typeService.save(typeAddReq.getType());
            return ok(new CompletionResponse(true));
        } catch (ItemAlreadyExistsException e) {
            return status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<CompletionResponse> delete(@RequestBody TypeDeleteRequest typeDeleteReq) {
        long idToDelete = getIdFromDTOList(typeService.getAll(), typeDeleteReq.getId());
        try {
            typeService.delete(idToDelete);
            return ok(new CompletionResponse(true));
        } catch (ItemNotExistException e) {
            return status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/update")
    public ResponseEntity<CompletionResponse> update(@RequestBody TypeUpdateRequest typeUpdateReq) {
        long idToMod = getIdFromDTOList(typeService.getAll(), typeUpdateReq.getId());
        try {
            typeService.update(idToMod, typeUpdateReq.getVal());
            return ok(new CompletionResponse(true));
        } catch (ItemAlreadyExistsException e) {
            return status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
