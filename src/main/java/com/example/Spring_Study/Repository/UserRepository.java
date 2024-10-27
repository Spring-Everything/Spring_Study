package com.example.Spring_Study.Repository;

import com.example.Spring_Study.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUid(String uid);
    boolean existsByUid(String uid);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
