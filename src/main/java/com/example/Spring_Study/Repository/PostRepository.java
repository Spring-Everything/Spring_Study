package com.example.Spring_Study.Repository;

import com.example.Spring_Study.Entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    List<PostEntity> findPostByUserId(Long userId);
}
