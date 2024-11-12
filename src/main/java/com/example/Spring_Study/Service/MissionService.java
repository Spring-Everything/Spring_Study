package com.example.Spring_Study.Service;

import com.example.Spring_Study.DTO.MissionDTO;
import com.example.Spring_Study.DTO.UserMissionDTO;
import com.example.Spring_Study.Entity.UserEntity;
import com.example.Spring_Study.Entity.UserMissionEntity;
import com.example.Spring_Study.Enum.StatusEnum;
import com.example.Spring_Study.Entity.MissionEntity;
import com.example.Spring_Study.Repository.MissionRepository;
import com.example.Spring_Study.Repository.PostRepository;
import com.example.Spring_Study.Repository.UserMissionRepository;
import com.example.Spring_Study.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MissionService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final MissionRepository missionRepository;
    private final UserMissionRepository userMissionRepository;

    // 미션 디폴트 데이터
    @Bean
    public CommandLineRunner createDefaultMissions() {
        return args -> {
            List<MissionEntity> challenges = List.of(
                    new MissionEntity(null, "[11월 미션] 좋아요 10번 누르기", "11월 미션입니다. 아무 게시글에 좋아요를 누적해서 10번 달면 성공입니다. 이번 달에 게시글 좋아요를 많이 표시하고 미션을 성공해보세요!'", LocalDate.of(2024, 8, 1), LocalDate.of(2024, 8, 31), StatusEnum.진행중, 0, 0, "https://nongburang-images.s3.ap-northeast-2.amazonaws.com/challenge_24_08_7.png"),
                    new MissionEntity(null, "[11월 미션] 좋아요 100번 누르기", "11월 미션입니다. 아무 게시글에 좋아요를 누적해서 100번 달면 성공입니다. 이번 달에 게시글 좋아요를 많이 표시하고 미션을 성공해보세요!'", LocalDate.of(2024, 8, 1), LocalDate.of(2024, 8, 31), StatusEnum.진행중, 0, 0, "https://nongburang-images.s3.ap-northeast-2.amazonaws.com/challenge_24_08_14.png"),
                    new MissionEntity(null, "[11월 미션] 좋아요 1000번 누르기", "11월 미션입니다. 아무 게시글에 좋아요를 누적해서 1000번 달면 성공입니다. 이번 달에 게시글 좋아요를 많이 표시하고 미션을 성공해보세요!'", LocalDate.of(2024, 8, 1), LocalDate.of(2024, 8, 31), StatusEnum.진행중, 0, 0, "https://nongburang-images.s3.ap-northeast-2.amazonaws.com/challenge_24_08_20.png"),
                    new MissionEntity(null, "[11월 미션] 좋아요 10000번 누르기", "11월 미션입니다. 아무 게시글에 좋아요를 누적해서 10000번 달면 성공입니다. 이번 달에 게시글 좋아요를 많이 표시하고 미션을 성공해보세요!'", LocalDate.of(2024, 7, 1), LocalDate.of(2024, 7, 31), StatusEnum.진행중, 0, 0, "https://nongburang-images.s3.ap-northeast-2.amazonaws.com/challenge_24_07_7.png")
            );
            for (MissionEntity challenge : challenges) {
                if (missionRepository.findByTitle(challenge.getTitle()).isEmpty()) {
                    missionRepository.save(challenge);
                }
            }
        };
    }

    // 현재 시간에 따른 챌린지 상태 자동 변경 메서드
    private StatusEnum calculateStatus(LocalDate startDate, LocalDate endDate) {
        LocalDate today = LocalDate.now();
        if (today.isBefore(startDate)) {
            return StatusEnum.대기중;
        } else if (today.isAfter(endDate)) {
            return StatusEnum.종료;
        } else {
            return StatusEnum.진행중;
        }
    }

    // 유저 챌린지 성공 여부 자동 변경 메서드
    private int getChallengeGoal(Long challengeId) {
        return switch (challengeId.intValue()) {
            case 1 -> 10;
            case 2 -> 100;
            case 3 -> 1000;
            case 4 -> 10000;
            default -> Integer.MAX_VALUE;
        };
    }

    // 해당 유저가 미션 참여 이후의 좋아요 개수를 계산하는 메서드
    public int calculateCompletedAttempts(UserEntity user, LocalDate participationDate) {
        int count = postRepository.countByUserAndDateAfter(user, participationDate);
        logger.info("유저 uid : {}, 미션 참여 날짜 : {}, 참여 날짜 이후 좋아요 개수 : {}", user.getUid(), participationDate, count);
        return count;
    }

    // 미션 생성
    public MissionDTO createdMission(MissionDTO missionDTO) {
        MissionEntity missionEntity = missionDTO.dtoToEntity();
        missionEntity.setStatus(calculateStatus(missionEntity.getStartDate(), missionEntity.getEndDate()));
        MissionEntity mission = missionRepository.save(missionEntity);
        logger.info("챌린지 생성 완료!");
        return MissionDTO.entityToDto(mission);
    }

    // 미션 전체 조회
    public List<MissionDTO> getAllMission() {
        return missionRepository.findAll()
                .stream()
                .map(MissionDTO::entityToDto)
                .collect(Collectors.toList());
    }

    // 미션 제목으로 검색
    public List<MissionDTO> findMissionByTitle(String title) {
        return missionRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(MissionDTO::entityToDto)
                .collect(Collectors.toList());
    }

    // 미션 내용으로 검색
    public List<MissionDTO> findMissionByContent(String content) {
        return missionRepository.findByContentContainingIgnoreCase(content)
                .stream()
                .map(MissionDTO::entityToDto)
                .collect(Collectors.toList());
    }

    // 진행 중인 미션만 검색
    public List<MissionDTO> getGoingChallenges() {
        return missionRepository.findAll().stream()
                .filter(challenge -> challenge.getStatus() == StatusEnum.진행중)
                .map(MissionDTO::entityToDto)
                .collect(Collectors.toList());
    }

    // 참여 중인 미션만 검색
    public List<MissionDTO> getParticipatingChallenges() {
        return missionRepository.findAll()
                .stream()
                .filter(challenge -> challenge.getStatus() == StatusEnum.참여)
                .map(MissionDTO::entityToDto)
                .collect(Collectors.toList());
    }

    // 종료된 미션만 검색
    public List<MissionDTO> getCompletedChallenges() {
        return missionRepository.findAll()
                .stream()
                .filter(challenge -> challenge.getStatus() == StatusEnum.종료)
                .map(MissionDTO::entityToDto)
                .collect(Collectors.toList());
    }

    // 대기 중인 미션만 검색
    public List<MissionDTO> getPendingChallenges() {
        return missionRepository.findAll()
                .stream()
                .filter(challenge -> challenge.getStatus() == StatusEnum.대기중)
                .map(MissionDTO::entityToDto)
                .collect(Collectors.toList());
    }

    // 미션 수정
    public MissionDTO updateMission(MissionDTO missionDTO) {
        MissionEntity missionEntity = missionRepository.findById(missionDTO.getId()).orElseThrow();
        missionEntity.setTitle(missionDTO.getTitle());
        missionEntity.setContent(missionDTO.getContent());
        missionEntity = missionRepository.save(missionEntity);
        return MissionDTO.entityToDto(missionEntity);
    }

    // 미션 삭제
    public MissionDTO deleteMission(MissionDTO missionDTO) {
        MissionEntity missionEntity = missionDTO.dtoToEntity();
        missionRepository.delete(missionEntity);
        return MissionDTO.entityToDto(missionEntity);
    }

    // 유저 미션 참여
    public UserMissionDTO participateInMission(Long missionId, Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow();
        MissionEntity mission = missionRepository.findById(missionId).orElseThrow();

        // 미션이 종료 상태인 경우 참여 불가
        if (mission.getStatus() == StatusEnum.종료) {
            throw new RuntimeException("이 미션은 종료되어 더 이상 참여할 수 없습니다");
        }

        // 이미 참여 중인 미션이 있는지 확인
        boolean isAlreadyParticipating = userMissionRepository.findByUserId(userId)
                .stream()
                .anyMatch(userMission -> userMission.getMission().getId().equals(missionId));
        if (isAlreadyParticipating) {
            throw new RuntimeException("이미 참여중인 챌린지입니다");
        }

        LocalDate participationDate = LocalDate.now();
        UserMissionEntity userMission = UserMissionEntity.builder()
                .user(user)
                .mission(mission)
                .participateStatus(StatusEnum.참여)
                .participationDate(participationDate)
                .completedAttempts(0)
                .build();

        // 참여 인원 수 증가
        mission.setParticipantsCount(mission.getParticipantsCount() + 1);
        missionRepository.save(mission);

        UserMissionEntity createdUserChallenge = userMissionRepository.save(userMission);
        logger.info("저장 후 UserChallenge : {}", createdUserChallenge);
        logger.info("uid {}이(가) 챌린지 Id {}번에 참여했습니다", userId, missionId);
        return UserMissionDTO.entityToDto(createdUserChallenge);
    }

    // 유저 미션 성공 처리
    public void completeMission(Long missionId, Long userId) {
        UserMissionEntity userMission = userMissionRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("미션 참여 내역이 없습니다."));

        // 미션이 성공 상태로 변경된 경우
        userMission.setParticipateStatus(StatusEnum.성공);
        userMissionRepository.save(userMission);

        // 미션 성공 인원 수 증가
        MissionEntity mission = missionRepository.findById(missionId).orElseThrow();
        mission.setCompletedPeople(mission.getCompletedPeople() + 1);
        missionRepository.save(mission);

        logger.info("uid {}이(가) 챌린지 Id {}번을 성공했습니다", userId, missionId);
    }

    // 유저 미션 성공 여부
    public UserMissionDTO updateUserMissionStatus(Long userChallengeId, StatusEnum status) {
        UserMissionEntity userChallenge = userMissionRepository.findById(userChallengeId).orElseThrow();

        // 해당 유저에 대하여 한 번 성공했으면 CompletedAttempts 필드를 더 이상 증가시키지 않음
        if (userChallenge.getSuccessStatus() != StatusEnum.성공) {
            userChallenge.setSuccessStatus(status);
            if (status == StatusEnum.성공) {
                MissionEntity mission = userChallenge.getMission();
                mission.setCompletedPeople(mission.getCompletedPeople() + 1);
                missionRepository.save(mission);
            }
        }
        UserMissionEntity updatedUserMission = userMissionRepository.save(userChallenge);
        logger.info("유저 챌린지 ID {}번의 상태가 {}로 업데이트되었습니다", userChallengeId, status);
        return UserMissionDTO.entityToDto(updatedUserMission);
    }

    // 유저 미션 성공 여부 자동 변경
    public void checkAndUpdateMissionStatus(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow();
        List<UserMissionEntity> userMissions = userMissionRepository.findByUserId(userId);

        for (UserMissionEntity userMission : userMissions) {
            int completedAttempts = postRepository.countByUserAndDateAfter(user, userMission.getParticipationDate());
            userMission.setCompletedAttempts(completedAttempts);

            if (userMission.getCompletedAttempts() >= getChallengeGoal(userMission.getMission().getId())
                    && userMission.getSuccessStatus() != StatusEnum.성공) {
                userMission.setSuccessStatus(StatusEnum.성공);

                MissionEntity mission = userMission.getMission();
                mission.setCompletedPeople(mission.getCompletedPeople() + 1);
                missionRepository.save(mission);
            } else {
                userMissionRepository.save(userMission);
            }
        }
    }

    // 미션 참여 유저 전체 조회
    public List<UserMissionDTO> getAllUserMission() {
        return userMissionRepository.findAll()
                .stream()
                .map(UserMissionDTO::entityToDto)
                .collect(Collectors.toList());
    }
}
