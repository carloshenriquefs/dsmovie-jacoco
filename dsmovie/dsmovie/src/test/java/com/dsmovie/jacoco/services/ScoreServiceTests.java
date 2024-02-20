package com.dsmovie.jacoco.services;

import com.dsmovie.jacoco.dto.MovieDTO;
import com.dsmovie.jacoco.dto.ScoreDTO;
import com.dsmovie.jacoco.entities.MovieEntity;
import com.dsmovie.jacoco.entities.ScoreEntity;
import com.dsmovie.jacoco.entities.UserEntity;
import com.dsmovie.jacoco.repositories.MovieRepository;
import com.dsmovie.jacoco.repositories.ScoreRepository;
import com.dsmovie.jacoco.services.exceptions.ResourceNotFoundException;
import com.dsmovie.jacoco.tests.MovieFactory;
import com.dsmovie.jacoco.tests.ScoreFactory;
import com.dsmovie.jacoco.tests.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ScoreServiceTests {

    @InjectMocks
    private ScoreService service;
    @Mock
    private UserService userService;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private ScoreRepository scoreRepository;

    private Long existingMovieId, nonExistingMovieId;

    private UserEntity userEntity;
    private ScoreEntity scoreEntity;
    private MovieEntity movieEntity;

    private ScoreDTO scoreDTO;

    @BeforeEach
    void setUp() throws Exception {

        existingMovieId = 1L;
        nonExistingMovieId = 2L;

        userEntity = UserFactory.createUserEntity();
        scoreEntity = ScoreFactory.createScoreEntity();
        movieEntity = MovieFactory.createMovieEntity();

        scoreDTO = ScoreFactory.createScoreDTO();

        ScoreEntity score = new ScoreEntity();
        score.setMovie(movieEntity);
        score.setUser(userEntity);
        score.setValue(5.2);

        movieEntity.getScores().add(score);

        when(userService.authenticated()).thenReturn(userEntity);

        when(movieRepository.findById(existingMovieId)).thenReturn(Optional.of(movieEntity));
        when(scoreRepository.saveAndFlush(any())).thenReturn(scoreEntity);
        when(movieRepository.save(any())).thenReturn(movieEntity);

        when(movieRepository.findById(nonExistingMovieId)).thenReturn(Optional.empty());
    }

    @Test
    public void saveScoreShouldReturnMovieDTO() {

        MovieDTO result = service.saveScore(scoreDTO);

        assertNotNull(result);
    }

    @Test
    public void saveScoreShouldThrowResourceNotFoundExceptionWhenNonExistingMovieId() {

        MovieEntity movie = MovieFactory.createMovieEntity();
        movie.setId(nonExistingMovieId);
        UserEntity user = UserFactory.createUserEntity();
        ScoreEntity score = new ScoreEntity();

        score.setMovie(movie);
        score.setUser(user);
        score.setValue(4.5);
        movie.getScores().add(score);

        scoreDTO = new ScoreDTO(score);

        assertThrows(ResourceNotFoundException.class, () -> {
            service.saveScore(scoreDTO);
        });
    }
}