package com.switchteam.onoff.domain.grants.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GrantCheckResponseDto {

    private String selectionCriteria;
    private String requiredDocuments;

}
