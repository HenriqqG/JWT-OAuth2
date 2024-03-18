package com.org.jwtoauth.service;

import com.org.jwtoauth.model.dto.ResponseDTO;
import com.org.jwtoauth.model.entity.ApplicationUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    public ResponseDTO loginUser(String username, String password) throws Exception {
        try{
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            if(auth.isAuthenticated()){
                String accessToken = tokenService.generateJwt(auth);
                String refreshToken = refreshTokenService.createRefreshToken(username).getToken();

                Optional<ApplicationUser> user = userRepository.findByUsername(username);
                return user.map(applicationUser -> new ResponseDTO(applicationUser, accessToken, refreshToken)).orElseGet(ResponseDTO::new);
            }
            throw new Exception("Informações de login inválidas!");
        }catch (AuthenticationException e){
            e.printStackTrace();
            return new ResponseDTO("","");
        }
    }

    public ResponseDTO refreshAccessToken(ApplicationUser appUser) {
        String accessToken = tokenService.reGenerateJwt(appUser);
        Optional<ApplicationUser> user = userRepository.findByUsername(appUser.getUsername());
        return user.map(applicationUser -> new ResponseDTO(applicationUser, accessToken, "")).orElseGet(ResponseDTO::new);
    }

}
