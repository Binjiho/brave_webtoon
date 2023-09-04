package com.example.brave_webtoon.webtoon.service;

import com.example.brave_webtoon.webtoon.dto.*;
import com.example.brave_webtoon.webtoon.dto.admin.VoteResponseDto;
import com.example.brave_webtoon.webtoon.dto.admin.WebtoonResponseDto;
import com.example.brave_webtoon.webtoon.dto.admin.WebtoonRoleResponseDto;
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
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class WebtoonService {
    private final WebtoonRepository webtoonRepository;
    private final WebtoonRoleRepository webtoonRoleRepository;
    private final WebtoonRepositoryImpl webtoonRepositoryImp;
    private final WebtoonRoleRepositoryImpl webtoonRoleRepositoryImp;
    private final VoteRepositoryImpl voteRepository;

    public List<MainRequestDto> findMainWebtoonList(int pageSize, int offset, String title){
        List<MainResponseDto> response = new ArrayList<>();

        List<MainRequestDto> list = webtoonRepository.findWebtoonIdList(pageSize, offset, title);
        list.get(0).getWebtoonEntityList().stream().forEach(obj-> response.add(getWebtoonDto(obj.getId())));

        List<MainRequestDto> result = new ArrayList<>();
        result.add(MainRequestDto.builder()
                        .mainResponseDtoList(response)
                        .hasNext(list.get(0).isHasNext())
                        .lastOffset(list.get(0).getLastOffset())
                .build());
//        result.removeIf(Objects::isNull); //투표가 없어도 웹툰리스트를 불러와야함
        return result;
    }

    public MainResponseDto getWebtoonDto(Long id){
        MainResponseDto result = null;
        List<MainResponseDto> list = webtoonRepository.findWebtoonListWithVote(id);
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

    /**
     * Admin Webtoon
     * @return
     */
    public List<WebtoonResponseDto> findAllWebtoonList(int pageSize, int page, String search, String order){
        List<WebtoonResponseDto> result = webtoonRepository.findAllWebtoon(pageSize, page, search, order);
        return result;
    }

    @Transactional
    public Long transWebtoonDeleteYN(Long id, int deleteYn){
        Long result = 0L;
        Optional<WebtoonEntity> optionalWebtoonEntity = Optional.ofNullable(webtoonRepositoryImp.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("ID에 해당하는 웹툰이 존재하지 않습니다");
        }));
        if (optionalWebtoonEntity.isPresent()){
            WebtoonEntity webtoonEntity = WebtoonEntity.builder()
                    .id(optionalWebtoonEntity.get().getId())
                    .title(optionalWebtoonEntity.get().getTitle())
                    .hit(optionalWebtoonEntity.get().getHit())
                    .uploadPath(optionalWebtoonEntity.get().getUploadPath())
                    .deleteYn(deleteYn)
                    .build();
            result = webtoonRepositoryImp.save(webtoonEntity).getId();
        }
        return result;
    }

    public List<WebtoonRoleResponseDto> findAdminAllWebtoonRoleList(Long id, int pageSize, int page){
        List<WebtoonRoleResponseDto> result = webtoonRoleRepository.findAdminAllWebtoonRoleList(id,pageSize,page);
        return result;
    }

    @Transactional
    public Long transWebtoonRoleDeleteYN(Long id, int deleteYn){
        Long result = 0L;
        Optional<WebtoonRoleEntity> optionalWebtoonRoleEntity = Optional.ofNullable(webtoonRoleRepositoryImp.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("ID에 해당하는 웹툰Role이 존재하지 않습니다");
        }));
        if (optionalWebtoonRoleEntity.isPresent()){
            WebtoonRoleEntity webtoonRoleEntity = WebtoonRoleEntity.builder()
                    .id(optionalWebtoonRoleEntity.get().getId())
                    .title(optionalWebtoonRoleEntity.get().getTitle())
                    .name(optionalWebtoonRoleEntity.get().getName())
                    .hit(optionalWebtoonRoleEntity.get().getHit())
                    .webtoonEntity(optionalWebtoonRoleEntity.get().getWebtoonEntity())
                    .role(optionalWebtoonRoleEntity.get().getRole())
                    .uploadPath(optionalWebtoonRoleEntity.get().getUploadPath())
                    .deleteYn(deleteYn)
                    .build();
            result = webtoonRoleRepositoryImp.save(webtoonRoleEntity).getId();
        }
        return result;
    }

    public List<VoteResponseDto> findVoteList(Long roleId){
        List<VoteResponseDto> result = webtoonRoleRepository.findAllVoteList(roleId);
        return result;
    }

    @Transactional
    public Long transPersonUrl(Long voteId, String personUrl){
        Long result = 0L;
        Optional<VoteEntity> optionalVoteEntity = Optional.ofNullable(voteRepository.findById(voteId).orElseThrow(() -> {
            return new IllegalArgumentException("ID에 해당하는 웹툰Vote가 존재하지 않습니다");
        }));
        if (optionalVoteEntity.isPresent()){
            VoteEntity voteEntity = VoteEntity.builder()
                    .id(optionalVoteEntity.get().getId())
                    .webtoonEntity(optionalVoteEntity.get().getWebtoonEntity())
                    .webtoonRoleEntity(optionalVoteEntity.get().getWebtoonRoleEntity())
                    .personName(optionalVoteEntity.get().getPersonName())
                    .personUrl(personUrl)
                    .build();
            result = voteRepository.save(voteEntity).getId();
        }
        return result;
    }

}
