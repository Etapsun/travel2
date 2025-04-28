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


    @Column(name = "image_type")
    @JsonProperty("imageType")
    private Integer imageType;

    @Column(name = "upload_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss" )
    private LocalDateTime uploadTime;//上传时间

    @Column(name = "file_size")
    private Long fileSize;  // 新增文件大小字段（字节）

    @Column(name = "file_type")
    private String fileType; // 新增文件MIME类型字段

    @Column(name = "original_name")
    private String originalName; // 新增原始文件名

    @Column(name = "attraction_id")
    private Integer attractionId; // 景点id

    public LocalDateTime getCreatedAt() {
        return uploadTime;
    }



    public static class ImageType {
        public static final int AVATAR = 1;
        public static final int DYNAMIC = 2;
        public static final int COVER = 3;
    }

    public static class Type {
        public static final int USER_AVATAR = 1;      // 用户头像
        public static final int ATTRACTION_COVER = 2; // 景点封面
        public static final int ATTRACTION_DETAIL = 3; // 景点详情图
        public static final int PRODUCT_IMAGE = 4;    // 商品图片
        // 保持与现有代码中的定义一致
    }
}
