package com.switchteam.onoff.domain.property.repository;

import com.switchteam.onoff.domain.property.dto.PropertyCardDto;
import com.switchteam.onoff.domain.property.entity.Property;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query("""
        select new com.switchteam.onoff.domain.property.dto.PropertyCardDto(
            p.id,
            p.industry,
            lc.transactionType,
            lc.rent,
            lc.deposit,
            lc.salePrice,
            lc.premium,
            loc.address,
            p.exclusiveArea
        )
        from Property p
          join p.leaseCost lc
          join p.location loc
        """)
    List<PropertyCardDto> findAllCards();

    @Query("""
    select new com.switchteam.onoff.domain.property.dto.PropertyCardDto(
        p.id,
        p.industry,
        lc.transactionType,
        lc.rent,
        lc.deposit,
        lc.salePrice,
        lc.premium,
        loc.address,
        p.exclusiveArea
    )
    from Property p
      join p.leaseCost lc
      join p.location loc
    where p.id = :id
    """)
    Optional<PropertyCardDto> findCardById(@Param("id") Long id);
}
