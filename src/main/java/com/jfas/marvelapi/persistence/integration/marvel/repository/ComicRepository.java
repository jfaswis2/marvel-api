package com.jfas.marvelapi.persistence.integration.marvel.repository;

import com.jfas.marvelapi.dto.MyPageable;
import com.jfas.marvelapi.persistence.integration.marvel.dto.ComicDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ComicRepository {
    public List<ComicDto> findAll(MyPageable pageable, Long characterId) {
        return null;
    }

    public ComicDto findById(Long comicId) {
        return null;
    }
}
