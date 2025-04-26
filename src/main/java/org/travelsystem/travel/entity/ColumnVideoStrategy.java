package org.travelsystem.travel.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 专栏/视频/攻略表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="column_video_strategy")
@Entity
public class ColumnVideoStrategy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主键（MySQL/Auto）
    @Column(name = "id")
    @JsonProperty("id")
    private Long id; // 主键字段
    //private Integer id;

    @Column(name = "user_id")
    @JsonProperty("userId")
    private Integer userId;//关联用户表id

    @Column(name = "attraction_id")
    @JsonProperty("attractionId")
    private Integer attractionId;//关联景点表id

    @Column(name = "cover_image_id")
    @JsonProperty("coverImageId")
    private Integer coverImageId;//封面图id
}
