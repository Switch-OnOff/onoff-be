package com.switchteam.onoff.domain.grants.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class GrantCheckResponse {

    private String selectionCriteria;
    private String requiredDocuments;

}
