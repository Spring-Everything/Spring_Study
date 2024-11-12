package com.example.Spring_Study.Repository;

import com.example.Spring_Study.Entity.PostEntity;
import com.example.Spring_Study.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    List<PostEntity> findPostByUserId(Long userId);
    List<PostEntity> findByTitleContainingIgnoreCase(String title);
    List<PostEntity> findByContentContainingIgnoreCase(String content);
    int countByUserAndDateAfter(@Param("user") UserEntity user, @Param("date") LocalDate date);
}
