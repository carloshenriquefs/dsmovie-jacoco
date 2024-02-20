package com.dsmovie.jacoco.services;

import com.dsmovie.jacoco.dto.MovieDTO;
import com.dsmovie.jacoco.entities.MovieEntity;
import com.dsmovie.jacoco.repositories.MovieRepository;
import com.dsmovie.jacoco.services.exceptions.DatabaseException;
import com.dsmovie.jacoco.services.exceptions.ResourceNotFoundException;
import com.dsmovie.jacoco.tests.MovieFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class MovieServiceTests {

    @InjectMocks
    private MovieService service;

    @Mock
    private MovieRepository movieRepository;

    private MovieEntity movie;
    private MovieDTO movieDTO;
    private String movieTitle;
    private Long existingMovieId, nonExistingMovieId, dependentMovieId;
    private PageImpl<MovieEntity> page;

    @BeforeEach
    void setup() throws Exception {

        existingMovieId = 1L;
        nonExistingMovieId = 2L;
        dependentMovieId = 3L;
        movieTitle = "Test Movie";

        movie = MovieFactory.createMovieEntity();
        movieDTO = new MovieDTO(movie);

        page = new PageImpl<>(List.of(movie));

        when(movieRepository.searchByTitle(any(), (Pageable) any())).thenReturn(page);
        when(movieRepository.findById(existingMovieId)).thenReturn(Optional.of(movie));

        when(movieRepository.save(any())).thenReturn(movie);

        when(movieRepository.getReferenceById(existingMovieId)).thenReturn(movie);
        when(movieRepository.getReferenceById(nonExistingMovieId)).thenThrow(EntityNotFoundException.class);

        when(movieRepository.existsById(existingMovieId)).thenReturn(true);
        when(movieRepository.existsById(nonExistingMovieId)).thenReturn(false);
        when(movieRepository.existsById(dependentMovieId)).thenReturn(true);

        doNothing().when(movieRepository).deleteById(existingMovieId);
        doThrow(DataIntegrityViolationException.class).when(movieRepository).deleteById(dependentMovieId);
    }

    @Test
    public void findAllShouldReturnPagedMovieDTO() {

        Pageable pageable = PageRequest.of(0, 12);

        Page<MovieDTO> result = service.findAll(movieTitle, pageable);

        assertNotNull(result);
        assertEquals(result.getSize(), 1);
        assertEquals(result.iterator().next().getTitle(), movieTitle);
    }

    @Test
    public void findByIdShouldReturnMovieDTOWhenIdExists() {

        MovieDTO result = service.findById(existingMovieId);

        assertNotNull(result);
        assertEquals(result.getId(), existingMovieId);
        assertEquals(result.getTitle(), movieTitle);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

        assertThrows(ResourceNotFoundException.class, () -> service.findById(nonExistingMovieId));
    }

    @Test
    public void insertShouldReturnMovieDTO() {

        MovieDTO result = service.insert(movieDTO);

        assertNotNull(result);
        assertEquals(result.getId(), movie.getId());

    }

    @Test
    public void updateShouldReturnMovieDTOWhenIdExists() {

        MovieDTO result = service.update(existingMovieId, movieDTO);

        assertNotNull(result);
        assertEquals(result.getId(), existingMovieId);
        assertEquals(result.getTitle(), movieDTO.getTitle());
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

        assertThrows(ResourceNotFoundException.class, () -> service.update(nonExistingMovieId, movieDTO));
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {

        assertDoesNotThrow(() -> {
            service.delete(existingMovieId);
        });

    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

        assertThrows(ResourceNotFoundException.class, () -> service.delete(nonExistingMovieId));
    }

    @Test
    public void deleteShouldThrowDatabaseExceptionWhenDependentId() {

        assertThrows(DatabaseException.class, () -> service.delete(dependentMovieId));
    }
}
