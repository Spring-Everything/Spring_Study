package com.example.Spring_Study.Repository;

import com.example.Spring_Study.Entity.MissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MissionRepository extends JpaRepository<MissionEntity, Long> {
    Optional<MissionEntity> findByTitle(String title);
    List<MissionEntity> findByTitleContainingIgnoreCase(String title);
    List<MissionEntity> findByContentContainingIgnoreCase(String content);
}
