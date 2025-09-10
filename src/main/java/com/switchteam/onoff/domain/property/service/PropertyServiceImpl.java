package com.switchteam.onoff.domain.property.service;

import com.switchteam.onoff.domain.property.dto.PropertyCardDto;
import com.switchteam.onoff.domain.property.repository.PropertyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {
    private final PropertyRepository propertyRepository;

    @Override
    public List<PropertyCardDto> getCardDataList(){
        return propertyRepository.findAllCards();
    }

    @Override
    public PropertyCardDto findCardDataById(Long id) {
        return propertyRepository.findCardById(id)
                .orElseThrow(() -> new EntityNotFoundException("Property not found with id=" + id));
    }

}
