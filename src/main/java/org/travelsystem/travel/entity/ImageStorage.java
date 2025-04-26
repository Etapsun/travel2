package org.travelsystem.travel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 *  图片存储表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "image_storage")
public class ImageStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private Long id;

    @Column(name = "image_url")
    @JsonProperty("imageUrl")
    private String imageUrl;//图片存储路径

    @Column(name = "upload_user_id")
    @JsonProperty("uploadUserId")
    private Integer uploadUserId;//上传者id

    private enum image_tyoe{头像,动态,封面};
    @Column(name = "image_type")
    @JsonProperty("imageType")
    private image_tyoe imageType;

    @Column(name = "upload_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss" )
    private LocalDateTime uploadTime;//上传时间
}
