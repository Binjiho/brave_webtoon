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
@Table(name = "webtoon_vote")
public class VoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "webtoon_id")
//    private WebtoonEntity webtoonEntity;

    @ManyToOne
    @JoinColumn(name = "webtoon_role_id")
    private WebtoonRoleEntity webtoonRoleEntity;

    @Column(name= "person_name", length = 50, nullable = false)
    private String personName;

    @Column(name= "person_url", nullable = false)
    private String personUrl;

    @Column(name = "delete_yn" , columnDefinition = "tinyint(1)")
    @ColumnDefault("0")
    private Integer deleteYn;

    @CreationTimestamp
    @Column(name="created_date", updatable=false)
    private LocalDateTime createdDate;

}
