package com.example.Spring_Study.Repository;

import com.example.Spring_Study.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
