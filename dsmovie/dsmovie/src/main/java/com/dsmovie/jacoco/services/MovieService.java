package com.dsmovie.jacoco.services;

import com.dsmovie.jacoco.dto.MovieDTO;
import com.dsmovie.jacoco.entities.MovieEntity;
import com.dsmovie.jacoco.repositories.MovieRepository;
import com.dsmovie.jacoco.services.exceptions.DatabaseException;
import com.dsmovie.jacoco.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dsmovie.jacoco.constants.Constants.FALHA_INTEGRIDADE_REFERENCIAL;
import static com.dsmovie.jacoco.constants.Constants.RECURSO_NAO_ENCONTRADO;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;
    private MovieEntity entity;

    @Transactional(readOnly = true)
    public Page<MovieDTO> findAll(String title, Pageable pageable) {
        Page<MovieEntity> result = movieRepository.searchByTitle(title, pageable);
        return result.map(x -> new MovieDTO(x));
    }

    @Transactional(readOnly = true)
    public MovieDTO findById(Long id) {
        MovieEntity result = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RECURSO_NAO_ENCONTRADO));
        return new MovieDTO(result);
    }

    @Transactional
    public MovieDTO insert(MovieDTO dto) {
        MovieEntity entity = new MovieEntity();
        copyDtoToEntity(dto, entity);
        entity = movieRepository.save(entity);
        return new MovieDTO(entity);
    }

    @Transactional
    public MovieDTO update(Long id, MovieDTO dto) {
        try {
            MovieEntity entity = movieRepository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = movieRepository.save(entity);
            return new MovieDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(RECURSO_NAO_ENCONTRADO);
        }
    }

    public void delete(Long id) {
        if (!movieRepository.existsById(id))
            throw new ResourceNotFoundException(RECURSO_NAO_ENCONTRADO);
        try {
            movieRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(FALHA_INTEGRIDADE_REFERENCIAL);
        }
    }

    private void copyDtoToEntity(MovieDTO dto, MovieEntity entity) {
        entity.setTitle(dto.getTitle());
        entity.setScore(dto.getScore());
        entity.setCount(dto.getCount());
        entity.setImage(dto.getImage());
    }
}
