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
 * 动态页面表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dynamic_page")
public class DynamicPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private Long id;

    @Column(name = "user_id")
    @JsonProperty("userId")
    private long userId;

    @Column(name = "username")
    @JsonProperty("userName")
    private String userName;

    @JsonProperty("dynamic_cover")
    @Column(name = "dynamic_cover")
    private String dynamic_cover;//动态封面图

    @Column(name = "post_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:SS")
    private LocalDateTime post_time;//动态发布时间

    @JsonProperty("comments")
    @Column(name = "comments")
    private Long comments;//动态评论数

    @JsonProperty("likes")
    @Column(name = "likes")
    private Long likes;//动态点赞数

    @JsonProperty("shares")
    @Column(name = "shares")
    private Long shares;//动态分享数

    private enum status{正常,删除,编辑,违规,审核};

    @Column(name = "status")
    @JsonProperty("status")
    private status status;

}
