package com.example.Spring_Study.Entity;

import com.example.Spring_Study.Enum.StatusEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "missions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title; // 미션 제목
    private String content; // 미션 내용
    private LocalDate startDate; // 미션 시작일
    private LocalDate endDate; // 미션 종료일
    private StatusEnum status; // 미션 상태 (미션 시작, 미션 종료 등등)
    private int participantsCount = 0; // 미션 참여 인원
    private int completedPeople = 0; // 미션 성공 인원
    private String image; // 미션 이미지 (GCP 연결 없이 정적으로 URL 채울 예정)
}
