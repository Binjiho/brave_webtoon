package com.example.brave_webtoon.webtoon.entity;

import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Table(name = "webtoon_role")
public class WebtoonRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "webtoon_id")
    private WebtoonEntity webtoonEntity;

    @OneToMany(mappedBy = "webtoonRoleEntity")
    private List<VoteEntity> voteEntityList;

    @Column(name= "title", length = 50, nullable = false)
    private String title;

    @Column(name= "name", length = 50, nullable = false)
    private String name;

    @Column(name= "role", length = 50, nullable = false)
    private String role;

    @Column(name = "delete_yn" , columnDefinition = "tinyint(1)")
    @ColumnDefault("0")
    private int deleteYn;

    @Column(name = "hit" , columnDefinition = "tinyint(1)")
    @ColumnDefault("0")
    private int hit;

    @Column(name="upload_path", nullable = false)
    private String uploadPath;

    @CreationTimestamp
    @Column(name="created_date", updatable=false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name="modified_date", insertable=false)
    private LocalDateTime modifiedDate;
}
