package com.jfas.marvelapi.persistence.integration.marvel.repository;

import com.jfas.marvelapi.dto.MyPageable;
import com.jfas.marvelapi.persistence.integration.marvel.dto.CharacterDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CharacterRepository {
    public List<CharacterDto> findAll(MyPageable pageable, String name, int[] comics, int[] series) {
        return null;
    }

    public CharacterDto.CharacterInfoDto findInfoById(Long characterId) {
        return null;
    }
}
