package org.travelsystem.travel.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *动态详细页面
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "dynamic_detail_page")
@Entity
public class DynamicDetailPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    @JsonProperty("userId")
    private Integer userId;

    @Column(name = "username")
    @JsonProperty("userName")
    private String userName;

    @Column(name = "images")
    @JsonProperty("images")
    private String images;

    @Column(name = "text")
    @JsonProperty("text")
    private String text;//动态文本内容
}
