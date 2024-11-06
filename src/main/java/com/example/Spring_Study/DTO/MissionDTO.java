package com.example.Spring_Study.DTO;

import com.example.Spring_Study.Entity.MissionEntity;
import com.example.Spring_Study.Enum.StatusEnum;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MissionDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;
    private StatusEnum status;
    private int participantsCount;
    private int completedPeople;
    private String image;

    public static MissionDTO entityToDto(MissionEntity missionEntity) {
        return new MissionDTO(
                missionEntity.getId(),
                missionEntity.getTitle(),
                missionEntity.getContent(),
                missionEntity.getStartDate(),
                missionEntity.getEndDate(),
                missionEntity.getStatus(),
                missionEntity.getParticipantsCount(),
                missionEntity.getCompletedPeople(),
                missionEntity.getImage()
        );
    }

    public MissionEntity dtoToEntity() {
        return new MissionEntity(id, title, content, startDate, endDate, status, participantsCount, completedPeople, image);
    }
}
