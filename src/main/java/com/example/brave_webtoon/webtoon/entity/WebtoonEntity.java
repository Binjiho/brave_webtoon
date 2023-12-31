package com.example.brave_webtoon.webtoon.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "webtoon")
public class WebtoonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "title", length = 50, nullable = false)
    private String title;

    @JsonIgnore
    @OneToMany(mappedBy = "webtoonEntity")
    private List<WebtoonRoleEntity> webtoonRoleEntityList;

    @Column(name = "delete_yn" , columnDefinition = "tinyint(1)")
    @ColumnDefault("0")
    private Integer deleteYn;

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
