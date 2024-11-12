package com.example.Spring_Study.Service;

import com.example.Spring_Study.DTO.ChallengeDTO;
import com.example.Spring_Study.Entity.ChallengeEntity;
import com.example.Spring_Study.Entity.UserEntity;
import com.example.Spring_Study.Enum.StatusEnum;
import com.example.Spring_Study.Repository.ChallengeRepository;
import com.example.Spring_Study.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;

    // 유저 챌린지 상태 업데이트
    public ChallengeDTO updateUserBadgeStatus(Long challengeId, StatusEnum status) {
        ChallengeEntity badgeEntity = challengeRepository.findById(challengeId).orElseThrow(() -> new RuntimeException("챌린지 ID가 " + challengeId + "인 챌린지를 찾을 수 없습니다"));
        badgeEntity.setIsAchieved(status);
        if (status == StatusEnum.성공) {
            badgeEntity.setAchievedAt(LocalDate.now());
        }
        ChallengeEntity updatedBadge = challengeRepository.save(badgeEntity);
        logger.info("유저 뱃지 ID{}번의 상태가 {}(으)로 업데이트 되었습니다", challengeId, status);
        return ChallengeDTO.entityToDto(updatedBadge);
    }

    // 챌린지 목표 자동 달성
    public void checkAndUpdateChallengeStatus(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("유저의 ID가 " + userId + "인 사용자를 찾을 수 없습니다"));
        int likesCount = userEntity.getLikeCount();
        List<ChallengeEntity> userchallenge = challengeRepository.findByUserId(userId);
        for (ChallengeEntity challenge : userchallenge) {
            if (likesCount >= challenge.getRequiredLikeCount() && challenge.getIsAchieved() != StatusEnum.성공) {
                challenge.setIsAchieved(StatusEnum.성공);
                challenge.setAchievedAt(LocalDate.now());
                challengeRepository.save(challenge);
                logger.info("유저 ID{}번의 챌린지 ID{}번이 성공으로 업데이트 되었습니다", userId, challenge.getId());
            }
        }
    }
}
