// ImageStorageMapper.java
package org.travelsystem.travel.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.travelsystem.travel.entity.ImageStorage;

import java.util.List;

public interface ImageStorageMapper { // 移除 @Mapper 注解
    int insert(ImageStorage imageStorage);

    // ... 其他方法不变 ...

   // @Select("SELECT * FROM image_storage WHERE image_type = #{type} AND upload_user_id = #{refId}")
    List<ImageStorage> findByTypeAndRefId(@Param("type") Integer type, @Param("refId") Integer refId);

  //  @Select("SELECT image_url FROM image_storage WHERE image_type = #{type}")
    List<String> findUrlsByType(@Param("type") Integer type);
    List<ImageStorage> findByAttractionId(@Param("type") Integer imageType, @Param("attractionid") Integer attractionid);
}