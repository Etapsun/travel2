package org.travelsystem.travel.service;

import org.travelsystem.travel.DTO.AttractionDTO;

import java.math.BigDecimal;
import java.util.List;

public interface AttractionService {
    AttractionDTO getAttractionById(Long id);
    List<AttractionDTO> searchAttractionsByName(String name);
    List<AttractionDTO> getAttractionsByRatingRange(BigDecimal min, BigDecimal max);
    AttractionDTO createAttraction(AttractionDTO dto);
    AttractionDTO updateAttraction(AttractionDTO dto);
    void deleteAttraction(Long id);
    void incrementPageViews(Long id);

}