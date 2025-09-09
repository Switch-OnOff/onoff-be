package com.switchteam.onoff.domain.grants.controller;

import com.switchteam.onoff.domain.grants.domain.Grants;
import com.switchteam.onoff.domain.grants.dto.request.GrantsFilterRequest;
import com.switchteam.onoff.domain.grants.service.GrantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GrantsController {

    private final GrantsService grantsService;

    @GetMapping("/api/grants/top5")
    public List<Grants> getTop5Grants() {
        return grantsService.getTop5Grants();
    }

    @PostMapping("/api/grants/filter")
    public List<Grants> GrantsFilter(@RequestBody GrantsFilterRequest grantsFilterRequest) {
        return grantsService.filterGrants(grantsFilterRequest);
    }


}
