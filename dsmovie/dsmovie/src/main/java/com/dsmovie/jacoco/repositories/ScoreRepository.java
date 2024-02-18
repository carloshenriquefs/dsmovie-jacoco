package com.dsmovie.jacoco.repositories;

import com.dsmovie.jacoco.entities.ScoreEntity;
import com.dsmovie.jacoco.entities.ScoreEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<ScoreEntity, ScoreEntityPK> {
}
