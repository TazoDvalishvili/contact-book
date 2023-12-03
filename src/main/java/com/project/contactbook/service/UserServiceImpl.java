package com.project.contactbook.service;

import com.project.contactbook.config.JwtService;
import com.project.contactbook.dto.AuthDTO;
import com.project.contactbook.dto.UserDTO;
import com.project.contactbook.model.User;
import com.project.contactbook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Override
    public UserDTO registerUser(UserDTO userDTO) throws RuntimeException{
        if(userRepository.existsByUsername(userDTO.getUsername())) {
            log.error("Username: " + userDTO.getUsername() + " already exists!");
            throw new RuntimeException("Username: " + userDTO.getUsername() + " already exists!");
        }
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user = userRepository.save(user);
        return new UserDTO(user.getId(), user.getUsername(), user.getPassword());
    }

    @Override
    public AuthDTO login(UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
        if(authentication.isAuthenticated()){
            return AuthDTO.builder()
                    .accessToken(jwtService.GenerateToken(userDTO.getUsername())).build();
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }
}
