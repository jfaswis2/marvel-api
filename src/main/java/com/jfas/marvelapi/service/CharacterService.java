package com.jfas.marvelapi.service;

import com.jfas.marvelapi.dto.MyPageable;
import com.jfas.marvelapi.persistence.integration.marvel.dto.CharacterDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CharacterService {
    List<CharacterDto> findAll(MyPageable pageable, String name, int[] comics, int[] series);

    CharacterDto.CharacterInfoDto findInfoById(Long characterId);
}
