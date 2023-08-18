package com.example.brave_webtoon.webtoon.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Table(name = "webtoon")
public class WebtoonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "title", length = 50, nullable = false)
    private String title;

//    @Column(columnDefinition = "TEXT", nullable = false)
//    private String content;

    @Column(name = "delete_yn" , columnDefinition = "tinyint(1)")
    @ColumnDefault("0")
    private Integer deleteYn;

    @Column(name = "hit" , columnDefinition = "tinyint(1)")
    @ColumnDefault("0")
    private int hit;

    @Column(name="save_name", nullable = false)
    private String saveName;

    @Column(name="upload_path", nullable = false)
    private String uploadPath;

    @CreationTimestamp
    @Column(name="created_date", updatable=false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name="modified_date", insertable=false)
    private LocalDateTime modifiedDate;
}
