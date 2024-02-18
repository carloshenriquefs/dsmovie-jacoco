package com.dsmovie.jacoco.controllers;

import com.dsmovie.jacoco.dto.MovieDTO;
import com.dsmovie.jacoco.dto.ScoreDTO;
import com.dsmovie.jacoco.services.ScoreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/scores")
public class ScoreController {

    @Autowired
    private ScoreService service;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @PutMapping
    public ResponseEntity<MovieDTO> saveScore(@Valid @RequestBody ScoreDTO dto) {
        MovieDTO movieDTO = service.saveScore(dto);
        return ResponseEntity.ok().body(movieDTO);
    }
}
