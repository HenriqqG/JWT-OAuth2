package com.org.jwtoauth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.org.jwtoauth.model.entity.RefreshToken;
import com.org.jwtoauth.model.entity.ApplicationUser;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshToken createRefreshToken(String username) throws Exception {
        Optional<ApplicationUser> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            Instant now = Instant.now();
            RefreshToken refreshToken = RefreshToken.builder()
                    .userInfo(user.get())
                    .token(UUID.randomUUID().toString())
                    .expiryDate(now.plusSeconds(60 * 60 * 48))
                    .build();
            return refreshTokenRepository.save(refreshToken);
        }
        throw new Exception(String.format("Usuário %s inexistente", username));
    }

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Por favor, realize um novo logIn!");
        }
        return token;
    }
}