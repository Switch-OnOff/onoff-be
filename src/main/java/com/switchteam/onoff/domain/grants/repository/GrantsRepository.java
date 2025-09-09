package com.switchteam.onoff.domain.grants.repository;

import com.switchteam.onoff.domain.grants.domain.Grants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrantsRepository extends JpaRepository<Grants, Long> {

    //인기 5개
    List<Grants> findTop5ByOrderByServiceNameAsc();



}
