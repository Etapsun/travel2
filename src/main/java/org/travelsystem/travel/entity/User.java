package org.travelsystem.travel.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    @Column(name = "id")
    private Long id;

    @JsonProperty("name")
    @Column(unique = true, nullable = false)
    private String name;

    @JsonProperty("password")
    @Column(nullable = false)
    @TableField(select = false)
    private String password;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private UserDetailed userDetailed;//关联详细信息
}
