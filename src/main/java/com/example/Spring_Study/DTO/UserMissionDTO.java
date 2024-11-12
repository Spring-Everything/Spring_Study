package com.example.Spring_Study.DTO;

import com.example.Spring_Study.Entity.MissionEntity;
import com.example.Spring_Study.Entity.UserEntity;
import com.example.Spring_Study.Entity.UserMissionEntity;
import com.example.Spring_Study.Enum.StatusEnum;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserMissionDTO {
    private Long id;
    private StatusEnum participateStatus;
    private StatusEnum successStatus;
    private int completedAttempts;
    private LocalDate participationDate;
    private Long userId;
    private Long missionId;
    private UserDTO user;
    private MissionDTO mission;

    public static UserMissionDTO entityToDto(UserMissionEntity userMissionEntity) {
        return new UserMissionDTO(
                userMissionEntity.getId(),
                userMissionEntity.getParticipateStatus(),
                userMissionEntity.getSuccessStatus(),
                userMissionEntity.getCompletedAttempts(),
                userMissionEntity.getParticipationDate(),
                userMissionEntity.getUser().getId(),
                userMissionEntity.getMission().getId(),
                UserDTO.entityToDto(userMissionEntity.getUser()),
                MissionDTO.entityToDto(userMissionEntity.getMission())
        );
    }

    public UserMissionEntity dtoToEntity(UserEntity user, MissionEntity mission) {
        return new UserMissionEntity(id, participateStatus, successStatus, completedAttempts, participationDate, user, mission);
    }
}
