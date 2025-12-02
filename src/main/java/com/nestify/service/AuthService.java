package com.nestify.service;

import com.nestify.dto.LoginRequestDto;
import com.nestify.dto.LoginResponseDto;
import com.nestify.dto.SignUpRequestDto;
import com.nestify.dto.UserDto;
import com.nestify.entity.User;
import com.nestify.entity.enums.Role;
import com.nestify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    public UserDto signup(SignUpRequestDto signUpRequestDto) {
        log.info("Signing up new user with email id: {}", signUpRequestDto.getEmail());
        User user = userRepository.findByEmail(signUpRequestDto.getEmail()).orElse(null);
        if (user!= null) throw new RuntimeException("User already exists with email id: " + signUpRequestDto.getEmail());

        User newUser = User.builder()
                .email(signUpRequestDto.getEmail())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .firstName(signUpRequestDto.getFirstName())
                .lastName(signUpRequestDto.getLastName())
                .roles(Set.of(Role.GUEST))
                .build();

        newUser = userRepository.save(newUser);
        return modelMapper.map(newUser, UserDto.class);
    }


    public String[] login(LoginRequestDto loginRequestDto) {
        log.info("Login request received: {}", loginRequestDto);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
        );
        log.info("Login authenticated");
        User user = (User) authentication.getPrincipal();
        if (user == null) throw new AuthenticationServiceException("Invalid username or password");
        String[] tokens = new String[2];
        tokens[0] = jwtService.generateAccessToken(user);
        tokens[1] = jwtService.generateRefreshToken(user);

        return tokens;
    }

    public String refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AuthenticationServiceException("User from the token not found"));

        return jwtService.generateAccessToken(user);
    }
}
