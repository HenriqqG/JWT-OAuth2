package com.org.jwtoauth.service;

import com.org.jwtoauth.model.entity.ApplicationUser;
import com.org.jwtoauth.model.entity.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Transactional
    public ApplicationUser registerUser(String username, String password, String role) throws Exception{
        if(userRepository.findByUsername(username).isPresent()){
            throw new Exception("Não foi possível cadastrar novo usuário: Usuário já existe");
        }
        String encodedPass = passwordEncoder.encode(password);
        Optional<Role> userRole = roleRepository.findByAuthority(role);
        if(userRole.isPresent()){
            Set<Role> authority = new HashSet<>();
            authority.add(userRole.get());

            return userRepository.save(new ApplicationUser(username, encodedPass, authority));
        }
        throw new Exception(String.format("Não foi possível cadastrar novo usuário: Role %s inexistente", role));
    }
}
