package com.example.brave_webtoon.security.dto;

import com.example.brave_webtoon.security.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
//@AllArgsConstructor
public class UserDetailsDto implements UserDetails {

    private MemberDto memberDto;
//    private Collection<? extends GrantedAuthority> authorities;

    private MemberEntity memberEntity;
    public UserDetailsDto(MemberEntity memberEntity) { this.memberEntity = memberEntity;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectRole=new ArrayList<>();
        collectRole.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                //ADMIN -> ROLE_ADMIN -> Authentication  권한 등록
                return "ROLE_"+memberEntity.getRole().toString();  // 사용자 권한 설정  -> ROLE_~
            }
        });

        return collectRole;
    }

    @Override
    public String getPassword() {
        return memberEntity.getUserPw();
    }

    @Override
    public String getUsername() {
        return memberEntity.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}
