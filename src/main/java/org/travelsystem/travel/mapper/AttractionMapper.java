package org.travelsystem.travel.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.travelsystem.travel.entity.Attraction;

import java.util.List;

@Mapper
public interface AttractionMapper {
    int insertAttraction(Attraction attraction);
    int deleteAttractionById(Long id);
    int updateAttraction(Attraction attraction);
    Attraction selectAttractionById(Long id);
    List<Attraction> selectAllAttractions();
}