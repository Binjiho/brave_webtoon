package com.example.brave_webtoon.security.service;

import com.example.brave_webtoon.security.constant.Role;
import com.example.brave_webtoon.security.dto.MemberDto;
import com.example.brave_webtoon.security.entity.MemberEntity;
import com.example.brave_webtoon.security.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberJoinService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    public Long memberSignUp(MemberDto memberDto) {
        int rs = memberIdCheck(memberDto);
        if (rs==0){
            //Dto -> Entity 변환
            MemberEntity memberEntity = new MemberEntity().builder()
                    .userId(memberDto.getUserId())
                    .userPw(bCryptPasswordEncoder.encode(memberDto.getUserPw()))
                    .htel(memberDto.getHtel())
                    .name(memberDto.getName())
                    .role(Role.valueOf("ADMIN"))
                    .build();
            memberRepository.save(memberEntity);
            return memberEntity.getId();
        }else{
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public int memberIdCheck(MemberDto memberDto) {
        Optional<MemberEntity> memberEntityOptional = memberRepository.findByUserId(memberDto.getUserId());

        if(memberEntityOptional.isPresent()) {
            return 1;
        }else{
            return 0;
        }
    }
}
