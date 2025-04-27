package org.travelsystem.travel.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.mapstruct.Mapper;
import org.travelsystem.travel.DTO.FileResponseDTO;
import org.travelsystem.travel.DTO.FileUploadDTO;
import org.travelsystem.travel.entity.ImageStorage;


@Mapper
public interface ImageStorageMapper {
    @Insert("INSERT INTO image_storage(image_url, upload_user_id, image_type, upload_time, file_size, file_type, original_name) " +
            "VALUES(#{imageUrl}, #{uploadUserId}, #{imageType}, #{uploadTime}, #{fileSize}, #{fileType}, #{originalName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ImageStorage imageStorage);
}