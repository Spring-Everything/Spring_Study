package com.example.Spring_Study.Repository;

import com.example.Spring_Study.Entity.ChallengeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<ChallengeEntity, Long> {
    List<ChallengeEntity> findByUserId(Long userId);
}
