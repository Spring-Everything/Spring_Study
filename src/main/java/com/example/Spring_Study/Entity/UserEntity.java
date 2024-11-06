package com.example.Spring_Study.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
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
    private int likeCount = 0;

    // 유저가 좋아요를 누른 게시물 ID 목록
    @ElementCollection
    @CollectionTable(name = "user_liked_posts", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "post_id")
    private Set<Long> likedPosts = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChallengeEntity> challenge;

    // 유저 엔티티와 챌린지 엔티티 간의 양방향 관계를 적절히 설정하기 위해 사용
    public void setChallenge(List<ChallengeEntity> challenges) {
        this.challenge = challenges;
        if (challenges != null) {
            challenges.forEach(challenge -> challenge.setUser(this));
        }
    }
}
