package com.example.Spring_Study.DTO;

import com.example.Spring_Study.Entity.ChallengeEntity;
import com.example.Spring_Study.Entity.UserEntity;
import com.example.Spring_Study.Enum.StatusEnum;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChallengeDTO {
    private Long id;
    private String title;
    private String description;
    private StatusEnum isAchieved;
    private LocalDate achievedAt;
    private int requiredLikeCount;

    public static ChallengeDTO entityToDto(ChallengeEntity challengeEntity){
        return new ChallengeDTO(
                challengeEntity.getId(),
                challengeEntity.getTitle(),
                challengeEntity.getDescription(),
                challengeEntity.getIsAchieved(),
                challengeEntity.getAchievedAt(),
                challengeEntity.getRequiredLikeCount()
        );
    }

    // 무한 순환 참조 방지를 위해 유저에 대한 정보는 null로 설정
    public ChallengeEntity dtoToEntity(){
        return new ChallengeEntity(id, title, description, isAchieved, achievedAt, requiredLikeCount, null);
    }
}
