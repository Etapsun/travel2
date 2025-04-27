// ImageStorageMapper.java
package org.travelsystem.travel.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.travelsystem.travel.entity.ImageStorage;

public interface ImageStorageMapper { // 移除 @Mapper 注解
    @Insert("INSERT INTO image_storage(image_url, upload_user_id, image_type, upload_time, file_size, original_name) " +
            "VALUES(#{imageUrl}, #{uploadUserId}, #{imageType}, #{uploadTime}, #{fileSize}, #{originalName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ImageStorage imageStorage);
}