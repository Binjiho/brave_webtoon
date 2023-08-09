package com.example.brave_webtoon.base.service;

import com.example.brave_webtoon.base.dto.WebtoonDto;
import com.example.brave_webtoon.base.entity.WebtoonEntity;
import com.example.brave_webtoon.base.entity.WebtoonRoleEntity;
import com.example.brave_webtoon.base.repository.WebtoonRepository;
import com.example.brave_webtoon.base.repository.WebtoonRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WebtoonService {
    private final WebtoonRepository webtoonRepository;
    private final WebtoonRoleRepository webtoonRoleRepository;

    public List<WebtoonEntity> findAllWebtoonList(){
        List<WebtoonEntity> result = webtoonRepository.findAll();
        return result;
    }

    public List<WebtoonRoleEntity> findAllWebtoonRoleList(Long webtoonId){
        List<WebtoonRoleEntity> result = webtoonRoleRepository.findAllByWebtoonId(webtoonId);
        return result;
    }
}
