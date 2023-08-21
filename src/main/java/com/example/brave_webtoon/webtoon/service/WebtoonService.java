package com.example.brave_webtoon.webtoon.service;

import com.example.brave_webtoon.webtoon.dto.VoteDto;
import com.example.brave_webtoon.webtoon.dto.VoteResultDto;
import com.example.brave_webtoon.webtoon.dto.WebtoonRoleDto;
import com.example.brave_webtoon.webtoon.entity.VoteEntity;
import com.example.brave_webtoon.webtoon.entity.WebtoonEntity;
import com.example.brave_webtoon.webtoon.entity.WebtoonRoleEntity;
import com.example.brave_webtoon.webtoon.repository.WebtoonRepository;
import com.example.brave_webtoon.webtoon.repository.WebtoonRoleRepository;
import com.example.brave_webtoon.webtoon.repository.impl.VoteRepositoryImpl;
import com.example.brave_webtoon.webtoon.repository.impl.WebtoonRepositoryImpl;
import com.example.brave_webtoon.webtoon.repository.impl.WebtoonRoleRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class WebtoonService {
    private final WebtoonRepository webtoonRepository;
    private final WebtoonRoleRepository webtoonRoleRepository;
    private final WebtoonRepositoryImpl webtoonRepositoryImp;
    private final WebtoonRoleRepositoryImpl webtoonRoleRepositoryImp;
    private final VoteRepositoryImpl voteRepository;

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

    /**
     * TODO: 비관적락 적용(멀티쓰레드의 경우)
     * @param voteDto
     */
    @Transactional
    public void saveVote(VoteDto voteDto){
        //webtoon hit 증가
        webtoonRepositoryImp.updateHit(voteDto.getWebtoonId());
        //webtoonRole hit 증가
        webtoonRoleRepositoryImp.updateHit(voteDto.getWebtoonRoleId());
        //vote 저장
        VoteEntity voteEntity = VoteEntity.builder()
                .webtoonEntity(WebtoonEntity.builder().id(voteDto.getWebtoonId()).build())
                .webtoonRoleEntity(WebtoonRoleEntity.builder().id(voteDto.getWebtoonId()).build())
                .personName(voteDto.getPersonName())
                .personUrl(voteDto.getPersonUrl())
                .deleteYn(0)
                .build();

        Long voteId = voteRepository.save(voteEntity).getId();
    }

    public List<VoteResultDto> findResultByWebtoonRoleId(Long webtoonRoleId){
        List<VoteResultDto> result = webtoonRoleRepository.findResultByWebtoonRoleId(webtoonRoleId);
        return result;
    }
}
