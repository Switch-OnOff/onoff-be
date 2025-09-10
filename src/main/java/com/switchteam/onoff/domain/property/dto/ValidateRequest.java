package com.switchteam.onoff.domain.property.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateRequest {
    @JsonProperty("b_no")
    private String b_no;

    @JsonProperty("start_dt")
    private String start_dt;

    @JsonProperty("p_nm")
    private String p_nm;
}
