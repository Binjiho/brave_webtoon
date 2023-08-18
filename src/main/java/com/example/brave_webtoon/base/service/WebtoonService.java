package com.example.brave_webtoon.base.service;

import com.example.brave_webtoon.base.dto.WebtoonRoleDto;
import com.example.brave_webtoon.base.entity.WebtoonEntity;
import com.example.brave_webtoon.base.entity.WebtoonRoleEntity;
import com.example.brave_webtoon.base.repository.WebtoonRepository;
import com.example.brave_webtoon.base.repository.WebtoonRepositoryImpl;
import com.example.brave_webtoon.base.repository.WebtoonRoleRepository;
import com.example.brave_webtoon.base.repository.WebtoonRoleRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WebtoonService {
    private final WebtoonRepository webtoonRepository;
    private final WebtoonRoleRepository webtoonRoleRepository;

    public List<WebtoonEntity> findAllWebtoonList(){
        List<WebtoonEntity> result = webtoonRepository.findAll();
        return result;
    }

    public List<WebtoonRoleDto> findAllWebtoonRoleList(Long webtoonId){
        List<WebtoonRoleDto> result = webtoonRoleRepository.findAllByWebtoonId(webtoonId);
        return result;
    }

    public List<WebtoonRoleDto> findByWebtoonRoleId(Long webtoonRoleId){
        List<WebtoonRoleDto> result = webtoonRoleRepository.findByWebtoonRoleId(webtoonRoleId);
        return result;
    }

//    @Transactional
//    public void saveVote(VoteDto voteDto){
//        //webtoon 검색 후 hit 증가
//        Optional<NoticeEntity> optionalNoticeEntity
//                = Optional.ofNullable(noticeRepository.findById(noticeId).orElseThrow(() -> {
//            return new IllegalArgumentException("아이디를 찾을 수 없습니다.");
//        }));
//
//        NoticeEntity noticeEntity = NoticeEntity.builder()
//                .title(noticeDto.getTitle())
//                .content(noticeDto.getContent())
//                .writer(noticeDto.getWriter())
//                .ord(noticeDto.getOrd())
//                .deleteYn(noticeDto.getDeleteYn())
//                .startDate(noticeDto.getStartDate())
//                .endDate(noticeDto.getEndDate())
//                .build();
//
//        Long noticeId = noticeRepository.save(noticeEntity).getId();
//
//        //webtoonRole 검색 후 hit 증가
//
//        //vote 저장
//
//    }

}