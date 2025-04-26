package org.travelsystem.travel.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 主推内容表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "main_recommendations")
public class MainRecommendations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private Long id;

    @Column(name = "cover_image")
    @JsonProperty("coverImage")
    private String coverImage;//推荐封面图

    @Column(name = "title")
    @JsonProperty("title")
    private String title;//推荐标题

    @Column(name = "content")
    @JsonProperty("content")
    private String content;//推荐内容
}
