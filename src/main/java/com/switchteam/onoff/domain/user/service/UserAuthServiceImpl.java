package com.switchteam.onoff.domain.user.service;

import com.switchteam.onoff.domain.user.domain.User;
import com.switchteam.onoff.domain.user.dto.UserLoginRequestDto;
import com.switchteam.onoff.domain.user.dto.UserSignupRequestDto;
import com.switchteam.onoff.global.exception.CustomException;
import com.switchteam.onoff.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private final UserService userService;

    @Override
    public User register(UserSignupRequestDto dto) {
        // 이메일 중복 체크
        userService.getUserByEmail(dto.getEmail())
                .ifPresent(u -> {throw new CustomException(ErrorCode.USER_ALREADY_EXISTS); });

        // 비밀번호 확인
        if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }

        // User 생성
        User user = User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .contact(dto.getContact())
                .build();

        // User 저장
        return userService.saveUser(user);
    }

    @Override
    public User login(UserLoginRequestDto dto) {
        // 이메일 존재 여부 확인
        User user = userService.getUserByEmail(dto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 비밀번호 일치 여부 확인
        if (!user.getPassword().equals(dto.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        // 로그인 성공
        return user;
    }
}
