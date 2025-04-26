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
 * 评论区表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="comment_section")
@Entity
public class CommentSection
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主键（MySQL/Auto）
    @Column(name = "id")
    @JsonProperty("id")
    private Long id; // 主键字段

    @Column(name = "content")
    @JsonProperty("content")
    private String content;//评论内容

    @Column(name = "user_nickname")
    @JsonProperty("userName")
    private String userName;//用户名称

    @JsonProperty("userAvatar")
    @Column(name = "user_avatar")
    private String user_avatar;//

    @Column(name = "comment_likes")
    @JsonProperty("commentLikes")
    private int commentlikes;//评论点赞数

    @Column(name = "comment_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:SS")
    private LocalDateTime commentTime;//评论发布时间

    @Column(name = "comment_ip")
    @JsonProperty("commentIp")
    private String commentIp;//用户评论的ip

    @Column(name = "is_deleted")
    @JsonProperty("isDeleted")
    private boolean isDeleted;//是否删除

    @Column(name = "attraction_id")
    @JsonProperty("attractionId")
    private Integer attractionId;//关联景点表的id

    @Column(name = "top_comment_id")
    @JsonProperty("topCommentId")
    private Integer topCommentId;//顶级评论id

    @Column(name = "reply_target_comment_id")
    @JsonProperty("replyTargetCommentId")
    private Integer replyTargetCommentId;//回复目标评论id


    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:SS")
    @Column(name = "edit_time")
    private LocalDateTime editTime;//评论最后编辑时间


}
