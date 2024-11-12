package com.example.Spring_Study.Entity;

import com.example.Spring_Study.Enum.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "usermissions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserMissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private StatusEnum participateStatus; // 미션 참여 여부
    private StatusEnum successStatus; // 미션 성공 여부
    private int completedAttempts; // 미션을 시작한 이후부터의 스마일 카운트 값
    private LocalDate participationDate; // 챌린지 참여 날짜 (누적 방지를 위해)

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "mission_id")
    private MissionEntity mission;
}
