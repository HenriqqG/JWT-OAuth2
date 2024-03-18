package com.org.jwtoauth.controller;

import com.org.jwtoauth.model.dto.RefreshTokenRequestDTO;
import com.org.jwtoauth.model.entity.RefreshToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/token")
@Tag(name="", description = "")
@RestController
public class TokenController {

    private final RefreshTokenService refreshTokenService;
    private final AuthenticationService authenticationService;
    @Operation(summary = "",
            description = "",
            security = {@SecurityRequirement(name = "bearer-key")})
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(userInfo -> {
                    LoginResponseDTO loginResponseDTO;
                    try {
                        loginResponseDTO = authenticationService.refreshAccessToken(userInfo);
                        loginResponseDTO.setRefresh_token(refreshTokenRequestDTO.getToken());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
                }).orElseThrow(() ->new RuntimeException("Refresh Token inválido!"));
    }
}
