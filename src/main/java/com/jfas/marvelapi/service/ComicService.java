package com.jfas.marvelapi.service;

import com.jfas.marvelapi.dto.MyPageable;
import com.jfas.marvelapi.persistence.integration.marvel.dto.ComicDto;

import java.util.List;

public interface ComicService {
    List<ComicDto> findAll(MyPageable pageable, Long characterId);

    ComicDto findById(Long comicId);
}
