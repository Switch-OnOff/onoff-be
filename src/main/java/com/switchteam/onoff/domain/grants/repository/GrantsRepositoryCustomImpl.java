package com.switchteam.onoff.domain.grants.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.switchteam.onoff.domain.grants.domain.Grants;
import com.switchteam.onoff.domain.grants.dto.request.GrantsFilterRequest;
import lombok.RequiredArgsConstructor;
import com.switchteam.onoff.domain.grants.domain.QGrants;


import java.util.List;

@RequiredArgsConstructor
public class GrantsRepositoryCustomImpl implements GrantsRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Grants> searchGrantsByFilters(GrantsFilterRequest filterRequest) {
        QGrants grants = QGrants.grants;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (filterRequest.getServiceStatus() != null && !filterRequest.getServiceStatus().isEmpty()) {
            BooleanBuilder statusBuilder = new BooleanBuilder();
            filterRequest.getServiceStatus().forEach(status -> {
                statusBuilder.or(grants.serviceStatus.eq(status));
                statusBuilder.or(grants.serviceStatus.contains("," + status));   // 콤마 뒤에 있는 경우
                statusBuilder.or(grants.serviceStatus.startsWith(status + ",")); // 맨 앞에 오는 경우
            });
            booleanBuilder.and(statusBuilder);
        }

        if (filterRequest.getLocation() != null && !filterRequest.getLocation().isEmpty()) {
            BooleanBuilder locationBuilder = new BooleanBuilder();
            filterRequest.getLocation().forEach(loc ->
                    locationBuilder.or(grants.location.eq(loc))
            );
            booleanBuilder.and(locationBuilder);
        }

        //후처리
        if (filterRequest.getIndustry() != null && !filterRequest.getIndustry().isEmpty()) {
            BooleanBuilder industryBuilder = new BooleanBuilder();
            filterRequest.getIndustry().forEach(ind ->
                    industryBuilder.or(grants.industry.contains(ind))
            );
            booleanBuilder.and(industryBuilder);
        }

        if (filterRequest.getKeyword() != null && !filterRequest.getKeyword().isEmpty()) {
            booleanBuilder.and(grants.serviceName.containsIgnoreCase(filterRequest.getKeyword()));
        }

        return jpaQueryFactory.selectFrom(grants)
                .where(booleanBuilder)
                .orderBy(grants.serviceName.asc())
                .fetch();
    }


}
