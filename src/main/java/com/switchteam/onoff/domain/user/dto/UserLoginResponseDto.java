package com.switchteam.onoff.domain.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponseDto {
    private Long userId;
    private String email;
    private String name;
    private String contact;
}
