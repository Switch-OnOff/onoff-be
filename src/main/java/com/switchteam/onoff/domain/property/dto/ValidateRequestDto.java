package com.switchteam.onoff.domain.property.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateRequestDto {
    @JsonProperty("b_no")
    private String bNo;

    @JsonProperty("start_dt")
    private String startDt;

    @JsonProperty("p_nm")
    private String pNm;
}
