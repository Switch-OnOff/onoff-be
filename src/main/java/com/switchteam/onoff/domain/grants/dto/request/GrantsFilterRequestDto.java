package com.switchteam.onoff.domain.grants.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GrantsFilterRequestDto {

    private List<String> serviceStatus;
    private List<String> location;
    private List<String> industry;
    private String keyword;
}
