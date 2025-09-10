package com.switchteam.onoff.domain.grants.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrantValidateRequestDto {
    private String serviceStatus;
    private String location;
    private String industry;
}
