package com.example.Spring_Study.Entity;

import com.example.Spring_Study.Enum.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "challenges")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChallengeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title; // 챌린지 제목
    private String description; // 챌린지 설명
    private StatusEnum isAchieved; // 챌린지 달성 여부
    private LocalDate achievedAt; // 챌린지 달성 날짜
    private int requiredLikeCount; // 챌린지 목표 좋아요

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
