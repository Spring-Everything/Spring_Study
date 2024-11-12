package com.example.Spring_Study.Repository;

import com.example.Spring_Study.Entity.UserMissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserMissionRepository extends JpaRepository<UserMissionEntity, Long> {
    List<UserMissionEntity> findByUserId(Long id);
}
