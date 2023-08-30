package com.example.brave_webtoon.webtoon.service;

import com.example.brave_webtoon.webtoon.dto.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public List<MainDto> findMainWebtoonList(String title){
        List<MainDto> result = new ArrayList<>();
        List<WebtoonEntity> list = webtoonRepository.findWebtoonIdList(title);
        list.stream().filter(Objects::nonNull).forEach(obj-> result.add(getWebtoonDto(obj.getId())));
//        List<MainDto> result = webtoonRepository.findWebtoonListWithVote(title);
        return result;
    }

    public MainDto getWebtoonDto(Long id){
        MainDto result = null;
        List<MainDto> list = webtoonRepository.findWebtoonListWithVote(id);
        if (!list.isEmpty()){
            result = list.get(0);
        }
        return result;
    }

    public List<WebtoonDto> findAllWebtoonRoleList(Long webtoonId, int pageSize, int offset){
        List<WebtoonDto> result = webtoonRepository.findAllRoleByWebtoonId(webtoonId,pageSize,offset);
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
                .webtoonRoleEntity(WebtoonRoleEntity.builder().id(voteDto.getWebtoonRoleId()).build())
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
