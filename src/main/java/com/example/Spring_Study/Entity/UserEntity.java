package com.example.Spring_Study.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uid;
    private String password;
    private String name;
    private String nickname;
    private String email;
    private String phone;

    // 유저가 좋아요를 누른 게시물 ID 목록
    @ElementCollection
    @CollectionTable(name = "user_liked_posts", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "post_id")
    private Set<Long> likedPosts = new HashSet<>();
}
