package com.jfas.marvelapi.persistence.integration.marvel.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.jfas.marvelapi.dto.MyPageable;
import com.jfas.marvelapi.persistence.integration.marvel.MarvelAPIConfig;
import com.jfas.marvelapi.persistence.integration.marvel.dto.ComicDto;
import com.jfas.marvelapi.service.HttpClientService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Repository
public class ComicRepository {

    private final MarvelAPIConfig marvelAPIConfig;
    private final HttpClientService httpClientService;
    @Value("${integration.marvel.base-path}")
    private String basePath;
    private String comicPath;

    public ComicRepository(MarvelAPIConfig marvelAPIConfig, HttpClientService httpClientService) {
        this.marvelAPIConfig = marvelAPIConfig;
        this.httpClientService = httpClientService;
    }

    @PostConstruct
    private void setPath() {
        comicPath = basePath.concat("/").concat("comics");
    }
    public List<ComicDto> findAll(MyPageable pageable, Long characterId) {
        Map<String, String> marvelQueryParams = getQueryParamsForFindAll(pageable, characterId);

        JsonNode response = httpClientService.doGet(comicPath, marvelQueryParams, JsonNode.class);

        return ComicMapper.toDtoList(response);
    }

    private Map<String, String> getQueryParamsForFindAll(MyPageable pageable, Long characterId) {
        Map<String, String> marvelQueryParams = marvelAPIConfig.getAuthenticationQueryParams();

        marvelQueryParams.put("offset", Long.toString(pageable.offset()));
        marvelQueryParams.put("limit", Long.toString(pageable.limit()));

        if (characterId != null && characterId > 0) {
            marvelQueryParams.put("characters", Long.toString(characterId));
        }

        return marvelQueryParams;
    }

    public ComicDto findById(Long comicId) {
        Map<String, String> marvelQueryParams = marvelAPIConfig.getAuthenticationQueryParams();

        String finalUrl = comicPath.concat("/").concat(Long.toString(comicId));
        JsonNode response = httpClientService.doGet(finalUrl, marvelQueryParams, JsonNode.class);

        return ComicMapper.toDtoList(response).get(0);
    }
}
