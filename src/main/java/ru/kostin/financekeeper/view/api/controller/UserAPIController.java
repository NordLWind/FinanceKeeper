package ru.kostin.financekeeper.view.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kostin.financekeeper.exception.ItemAlreadyExistsException;
import ru.kostin.financekeeper.service.UserService;
import ru.kostin.financekeeper.view.api.json.CompletionResponse;
import ru.kostin.financekeeper.view.api.json.user.AddUserRequest;
import ru.kostin.financekeeper.view.api.json.user.AuthRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserAPIController extends AbstractAPIController {
    private final UserService userService;

    @PostMapping("/auth")
    public ResponseEntity<CompletionResponse> auth(AuthRequest authRequest, HttpServletRequest servletRequest) {
        try {
            servletRequest.login(authRequest.getEmail(), authRequest.getPassword());
            return ok(new CompletionResponse(true));
        } catch (ServletException e) {
            return status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<CompletionResponse> add(@RequestBody AddUserRequest req) {
        try {
            userService.add(req.getName(), req.getEmail(), req.getPassword());
            return ok(new CompletionResponse(true));
        } catch (ItemAlreadyExistsException e) {
            return status(HttpStatus.BAD_REQUEST).body(new CompletionResponse(false));
        }
    }
}
