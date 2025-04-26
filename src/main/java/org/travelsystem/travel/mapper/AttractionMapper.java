package org.travelsystem.travel.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.travelsystem.travel.DTO.AttractionDTO;
import org.travelsystem.travel.entity.Attraction;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface AttractionMapper {
    Attraction selectByPrimaryKey(Long attractionId);
    @Select("SELECT * FROM attraction WHERE attraction_id = #{id}")
    Attraction selectById(@Param("id") Long id);

    @Select("SELECT * FROM attraction WHERE attraction_name LIKE CONCAT('%',#{name},'%')")
    List<Attraction> selectByName(@Param("name") String name);

    @Select("SELECT * FROM attraction WHERE attraction_rating BETWEEN #{min} AND #{max}")
    List<Attraction> selectByRatingRange(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

    @Update("UPDATE attraction SET page_views = page_views + 1 WHERE attraction_id = #{id}")
    int incrementPageViews(@Param("id") Long id);

    int insert(Attraction attraction);
    int update(Attraction attraction);
    int deleteById(Long id);
    List<AttractionDTO> selectAll();

    List<AttractionDTO> selectAllAttractionDetailed();
}