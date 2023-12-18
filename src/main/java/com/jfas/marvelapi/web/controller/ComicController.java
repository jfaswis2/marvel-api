package com.jfas.marvelapi.web.controller;

import com.jfas.marvelapi.dto.MyPageable;
import com.jfas.marvelapi.persistence.integration.marvel.dto.ComicDto;
import com.jfas.marvelapi.service.ComicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comics")
public class ComicController {

    private final ComicService comicService;

    public ComicController(ComicService comicService) {
        this.comicService = comicService;
    }

    @GetMapping
    public ResponseEntity<List<ComicDto>> findAll(
            @RequestParam(required = false) Long characterId,
            @RequestParam(defaultValue = "0") long offset,
            @RequestParam(defaultValue = "10") long limit
    ) {
        MyPageable pageable = new MyPageable(offset, limit);
        return ResponseEntity.ok(comicService.findAll(pageable, characterId));
    }


    @GetMapping("{comicId}")
    public ResponseEntity<ComicDto> findById(
            @PathVariable Long comicId
    ) {
        return ResponseEntity.ok(comicService.findById(comicId));
    }
}
