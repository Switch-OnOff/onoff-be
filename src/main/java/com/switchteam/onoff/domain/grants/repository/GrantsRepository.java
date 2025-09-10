package com.switchteam.onoff.domain.grants.repository;

import com.switchteam.onoff.domain.grants.domain.Grants;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrantsRepository extends JpaRepository<Grants, Long>, GrantsRepositoryCustom {

    //인기 5개
    List<Grants> findTop5ByOrderByServiceNameAsc();

    //키워드 검색
    List<Grants> findByServiceNameContaining(String keyword);

    @Query("SELECT g FROM Grants g " +
            "WHERE (:serviceStatus IS NULL OR g.serviceStatus LIKE %:serviceStatus%) " +
            "  AND (:location IS NULL OR g.location LIKE %:location%) " +
            "  AND (:industry IS NULL OR g.industry LIKE %:industry%)")
    List<Grants> searchGrantsByFilters(@Param("serviceStatus") String serviceStatus,
                                 @Param("location") String location,
                                 @Param("industry") String industry);


}
