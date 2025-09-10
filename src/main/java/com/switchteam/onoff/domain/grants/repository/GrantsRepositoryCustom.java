package com.switchteam.onoff.domain.grants.repository;

import com.switchteam.onoff.domain.grants.domain.Grants;
import com.switchteam.onoff.domain.grants.dto.request.GrantsFilterRequest;

import java.util.List;

public interface GrantsRepositoryCustom {

    List<Grants> searchGrantsByFilters(GrantsFilterRequest filterRequest);
}
