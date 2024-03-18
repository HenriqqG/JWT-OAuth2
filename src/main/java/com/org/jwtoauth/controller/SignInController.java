package com.org.jwtoauth.controller;

import com.org.jwtoauth.model.dto.RegisterDTO;
import com.org.jwtoauth.model.dto.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/signin")
@Tag(name="Sign In", description = "LogIn de Usuário")
@RestController
public class SignInController {

    private final AuthenticationService authenticationService;
    @Operation(summary = "",
            description = "")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping()
    public ResponseEntity<?> loginUser(@RequestBody RegisterDTO body) throws Exception {
        ResponseDTO loginResponse = authenticationService.loginUser(body.getCpf(), body.getPassword());
        return new ResponseEntity<>(loginResponse, HttpStatus.ACCEPTED);
    }
}
