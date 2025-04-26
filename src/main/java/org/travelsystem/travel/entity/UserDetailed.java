package org.travelsystem.travel.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.travelsystem.travel.entity.User;

/**
 *用户详细页面
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_detailed")
public class UserDetailed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    @Column(name = "id")
    private Long id;


    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private User user;  // 关联用户

    @JsonProperty("name")
    @Column(name = "name")
    private String name;

    @JsonProperty("age")
    @Column(name = "age")
    private int age;

    @JsonProperty("email")
    @Column(name = "email")
    private String email;

    @JsonProperty("phone")
    @Column(name = "phone")
    private String phone;

    @JsonProperty("city")
    @Column(name = "city")
    private String city;

    private enum sex{男,女};
    @JsonProperty("sex")
    @Column(name = "sex")
    @Enumerated(EnumType.STRING)
    private sex sex;

    @JsonProperty("usertext")
    @Column(name = "usertext")
    private String usertext;

    @JsonProperty("image")
    @Column(name = "image")
    private String image;//用户头像

    @JsonProperty("titleImage")
    @Column(name = "title_image")
    private String titleImage;//用户头图

    @Column(name = "avatarUrl")
    private String avatarUrl;  // 头像存储在阿里云 OSS 的路径

    @Column(name = "coverUrl")
    private String coverUrl;   // 头图路径

}