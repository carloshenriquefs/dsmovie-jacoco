package com.dsmovie.jacoco.services;

import com.dsmovie.jacoco.dto.MovieDTO;
import com.dsmovie.jacoco.dto.ScoreDTO;
import com.dsmovie.jacoco.entities.MovieEntity;
import com.dsmovie.jacoco.entities.ScoreEntity;
import com.dsmovie.jacoco.entities.UserEntity;
import com.dsmovie.jacoco.repositories.MovieRepository;
import com.dsmovie.jacoco.repositories.ScoreRepository;
import com.dsmovie.jacoco.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dsmovie.jacoco.constants.Constants.RECURSO_NAO_ENCONTRADO;

@Service
public class ScoreService {

    @Autowired
    private UserService userService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Transactional
    public MovieDTO saveScore(ScoreDTO dto) {

        UserEntity user = userService.authenticated();

        MovieEntity movie = movieRepository.findById(dto.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException(RECURSO_NAO_ENCONTRADO));

        ScoreEntity score = new ScoreEntity();
        score.setMovie(movie);
        score.setUser(user);
        score.setValue(dto.getScore());

        score = scoreRepository.saveAndFlush(score);

        double sum = 0.0;
        for (ScoreEntity s : movie.getScores()) {
            sum = sum + s.getValue();
        }

        double avg = sum / movie.getScores().size();

        movie.setScore(avg);
        movie.setCount(movie.getScores().size());

        movie = movieRepository.save(movie);

        return new MovieDTO(movie);
    }
}
