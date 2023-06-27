package ru.kostin.financekeeper.view.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kostin.financekeeper.exception.ItemAlreadyExistsException;
import ru.kostin.financekeeper.service.UserService;
import ru.kostin.financekeeper.view.api.json.CompletionResponse;
import ru.kostin.financekeeper.view.api.json.user.AddUserRequest;
import ru.kostin.financekeeper.view.api.json.user.AuthRequest;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserAPIController extends AbstractAPIController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<CompletionResponse> authenticateUser(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ok(new CompletionResponse(true));
        } catch (AuthenticationException e) {
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
