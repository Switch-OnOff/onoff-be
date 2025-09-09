package com.switchteam.onoff.domain.grants.service;

import com.switchteam.onoff.domain.grants.domain.Grants;
import com.switchteam.onoff.domain.grants.dto.request.GrantsFilterRequest;
import com.switchteam.onoff.domain.grants.repository.GrantsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GrantsService {

    private final GrantsRepository grantsRepository;

    public List<Grants> getTop5Grants() {
        return grantsRepository.findTop5ByOrderByServiceNameAsc();
    }

    public List<Grants> filterGrants(GrantsFilterRequest grantsFilterRequest) {
        return grantsRepository.searchGrantsByFilters(grantsFilterRequest);
    }


}
