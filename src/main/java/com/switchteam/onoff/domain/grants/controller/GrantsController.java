package com.switchteam.onoff.domain.grants.controller;

import com.switchteam.onoff.domain.grants.domain.Grants;
import com.switchteam.onoff.domain.grants.service.GrantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GrantsController {

    private final GrantsService grantsService;

    @GetMapping("/api/grants/top5")
    public List<Grants> getTop5Grants() {
        return grantsService.getTop5Grants();
    }


}
