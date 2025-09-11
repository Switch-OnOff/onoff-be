package com.switchteam.onoff.domain.ghostwrite.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GhostwriteRequestDto {
    List<String> sentences;
}
