package com.example.brave_webtoon.security.dto;

import com.example.brave_webtoon.security.constant.Role;
import com.example.brave_webtoon.security.entity.MemberEntity;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class MemberDto {
    private Long id;
    private String userId;
    private String userPw;
    private String name;
    private String htel;
    private Role role;
    private int fromSocial;
    private int deleteYn;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
