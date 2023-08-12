package com.example.brave_webtoon.security.service;

import com.example.brave_webtoon.security.entity.MemberEntity;
import com.example.brave_webtoon.security.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberLoginService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("================================================");
        log.info("UserDetailsService loadUserByUsername: " + username);

        MemberEntity memberEntity = memberRepository.findByUserId(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("존재하지 않는 회원입니다!");
        });

        log.info("================================================");
        log.info(memberEntity);

        return User.builder()
                .username(memberEntity.getUserId())
                .password(memberEntity.getUserPw())
                .roles(memberEntity.getRole().toString())
                .build();
//        return new MyUserDetails(memberEntity);
    }
}
