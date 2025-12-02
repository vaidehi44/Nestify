package com.nestify.controller;

import com.nestify.dto.LoginRequestDto;
import com.nestify.dto.LoginResponseDto;
import com.nestify.dto.SignUpRequestDto;
import com.nestify.dto.UserDto;
import com.nestify.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto>  signup(@RequestBody SignUpRequestDto signUpRequestDto) {
        return new ResponseEntity<>(authService.signup(signUpRequestDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto>  signup(
            @RequestBody LoginRequestDto loginRequestDto,
            HttpServletRequest request,
            HttpServletResponse response ) {
        String[] tokens = authService.login(loginRequestDto);

        Cookie cookie = new Cookie("refreshToken", tokens[1]);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return new ResponseEntity<>(new LoginResponseDto(tokens[0]), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request) {

        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("refreshToken"))
                .findFirst()
                .map(cookie -> cookie.getValue())
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found in cookies"));

        String accessToken = authService.refreshToken(refreshToken);
        return new ResponseEntity<>(new LoginResponseDto(accessToken), HttpStatus.OK);

    }
}
