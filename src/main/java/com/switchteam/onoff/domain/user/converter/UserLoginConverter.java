package com.switchteam.onoff.domain.user.converter;

import com.switchteam.onoff.domain.user.domain.User;
import com.switchteam.onoff.domain.user.dto.UserLoginResponseDto;

public class UserLoginConverter {

    public static UserLoginResponseDto toResponseDto(User user) {
        return UserLoginResponseDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .contact(user.getContact())
                .build();
    }
}
