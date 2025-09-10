package com.switchteam.onoff.domain.grants.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrantValidateRequest {
    private String serviceStatus;
    private String location;
    private String industry;
}
