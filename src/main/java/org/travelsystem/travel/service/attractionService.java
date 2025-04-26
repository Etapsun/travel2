package org.travelsystem.travel.service;

import jakarta.transaction.Transactional;
import org.travelsystem.travel.entity.Attraction;
import org.travelsystem.travel.service.impl.ResourceNotFoundException;

import java.util.List;

public interface attractionService {

    Attraction createattraction(Attraction attraction);
    void deleteattraction(Long id) throws ResourceNotFoundException;
    Attraction updateattraction(Long id, Attraction updateattraction) throws ResourceNotFoundException;
    List<Attraction> getAllattraction();
    Attraction getattractionById(Long id) throws ResourceNotFoundException;
    //attraction updateattraction(attraction attraction);
}
