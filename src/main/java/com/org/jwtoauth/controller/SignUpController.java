package com.org.jwtoauth.controller;

import com.org.jwtoauth.model.dto.RegisterDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/signup")
@Tag(name="", description = "")
@RestController
public class SignUpController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "",
            description = "")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO) throws Exception{
        authenticationService.registerUser();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
