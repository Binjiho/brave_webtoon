package com.example.brave_webtoon.security.entity;

import com.example.brave_webtoon.security.constant.Role;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(name = "admin_member")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "user_id", unique = true, length = 100, nullable = false)
    private String userId;

    @Column(name="user_pw", length = 255, nullable = false)
    private String userPw;

    @Column(name="name", length = 50, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String htel;

    @Column(name= "from_social", length = 1, columnDefinition = "tinyint(1)")
    @ColumnDefault("0")
    private Integer fromSocial;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "delete_yn")
    @ColumnDefault("0")
    private Integer deleteYn;

    @CreatedDate
    @Column(name="created_date", updatable=false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name="modified_date", insertable=false)
    private LocalDateTime modifiedDate;
}
