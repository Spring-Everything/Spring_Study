package com.example.Spring_Study.Controller;

import com.example.Spring_Study.DTO.ChallengeDTO;
import com.example.Spring_Study.Service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/challenge")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    // 유저 뱃지 상태 업데이트
    @PutMapping("/user/{challengeId}")
    public ResponseEntity<ChallengeDTO> updateUserBadgeStatus(@PathVariable Long challengeId, @RequestBody ChallengeDTO challengeDTO) {
        return ResponseEntity.ok(challengeService.updateUserBadgeStatus(challengeId, challengeDTO.getIsAchieved()));
    }
}
