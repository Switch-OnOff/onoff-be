package com.switchteam.onoff.domain.user.service;

import com.switchteam.onoff.domain.user.domain.User;
import com.switchteam.onoff.domain.user.dto.UserLoginRequestDto;
import com.switchteam.onoff.domain.user.dto.UserSignupRequestDto;

public interface UserAuthService {
    User register(UserSignupRequestDto dto);
    User login(UserLoginRequestDto dto);
}
