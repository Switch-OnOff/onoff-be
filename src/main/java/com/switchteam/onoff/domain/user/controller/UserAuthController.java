package com.switchteam.onoff.domain.user.controller;

import com.switchteam.onoff.domain.property.dto.PropertyCreateRequest;
import com.switchteam.onoff.domain.user.converter.UserLoginConverter;
import com.switchteam.onoff.domain.user.domain.User;
import com.switchteam.onoff.domain.user.dto.UserLoginRequestDto;
import com.switchteam.onoff.domain.user.dto.UserLoginResponseDto;
import com.switchteam.onoff.domain.user.dto.UserSignupRequestDto;
import com.switchteam.onoff.domain.user.service.UserAuthService;
import com.switchteam.onoff.global.common.CustomApiResponse;
import com.switchteam.onoff.global.common.SuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/auth")
public class UserAuthController {

    private final UserAuthService userAuthService;

    @PostMapping("/signup")
    public ResponseEntity<CustomApiResponse<PropertyCreateRequest>> register(@RequestBody @Valid UserSignupRequestDto dto) {
        User user = userAuthService.register(dto);
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.SIGNUP_SUCCESS));
    }

    @PostMapping("/login")
    public ResponseEntity<CustomApiResponse<UserLoginResponseDto>> login(@RequestBody @Valid UserLoginRequestDto dto) {
        User user = userAuthService.login(dto);
        UserLoginResponseDto responseDto = UserLoginConverter.toResponseDto(user);
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.LOGIN_SUCCESS, responseDto));
    }
}
