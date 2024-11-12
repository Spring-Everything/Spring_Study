package com.example.Spring_Study.Controller;

import com.example.Spring_Study.DTO.MissionDTO;
import com.example.Spring_Study.DTO.UserMissionDTO;
import com.example.Spring_Study.Service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mission")
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    // 미션 생성
    @PostMapping
    public ResponseEntity<MissionDTO> createMission(@RequestBody MissionDTO missionDTO) {
        return ResponseEntity.ok(missionService.createdMission(missionDTO));
    }

    // 미션 전체 조회
    @GetMapping("/all")
    public ResponseEntity<List<MissionDTO>> getAllMission() {
        return ResponseEntity.ok(missionService.getAllMission());
    }

    // 미션 제목으로 검색
    @GetMapping("/title")
    public ResponseEntity<List<MissionDTO>> findMissionByTitle(@RequestParam String title) {
        return ResponseEntity.ok(missionService.findMissionByTitle(title));
    }

    // 미션 내용으로 검색
    @GetMapping("/content")
    public ResponseEntity<List<MissionDTO>> findMissionByContent(@RequestParam String content) {
        return ResponseEntity.ok(missionService.findMissionByContent(content));
    }

    // 진행 중인 미션만 검색
    @GetMapping("/going")
    public ResponseEntity<List<MissionDTO>> getGoingChallenges(){
        return ResponseEntity.ok(missionService.getGoingChallenges());
    }

    // 참여 중인 미션만 검색
    @GetMapping("/participate")
    public ResponseEntity<List<MissionDTO>> getParticipatingChallenges(){
        return ResponseEntity.ok(missionService.getParticipatingChallenges());
    }

    // 진행 완료된 미션만 검색
    @GetMapping("/complete")
    public ResponseEntity<List<MissionDTO>> getCompletedChallenges(){
        return ResponseEntity.ok(missionService.getCompletedChallenges());
    }

    // 대기 중인 미션만 검색
    @GetMapping("/pending")
    public ResponseEntity<List<MissionDTO>> getPendingChallenges(){
        return ResponseEntity.ok(missionService.getPendingChallenges());
    }

    // 미션 수정
    @PutMapping
    public ResponseEntity<MissionDTO> updateMission(@RequestBody MissionDTO missionDTO) {
        return ResponseEntity.ok(missionService.updateMission(missionDTO));
    }

    // 미션 삭제
    @DeleteMapping
    public ResponseEntity<MissionDTO> deleteMission(@RequestBody MissionDTO missionDTO) {
        return ResponseEntity.ok(missionService.deleteMission(missionDTO));
    }

    // 유저 미션 참여
    @PostMapping("/{missionId}/{userId}")
    public ResponseEntity<UserMissionDTO> participateInMission(@PathVariable Long missionId, @PathVariable Long userId) {
        return ResponseEntity.ok(missionService.participateInMission(missionId, userId));
    }

    // 유저 미션 성공 여부
    @PutMapping("/{userMissionId}")
    public ResponseEntity<UserMissionDTO> updateUserMissionStatus(@PathVariable Long userMissionId, @RequestBody UserMissionDTO userMissionDTO) {
        return ResponseEntity.ok(missionService.updateUserMissionStatus(userMissionId, userMissionDTO.getSuccessStatus()));
    }

    // 미션 참여 유저 전체 조회
    @GetMapping("/usermission")
    public ResponseEntity<List<UserMissionDTO>> getAllUserMission() {
        return ResponseEntity.ok(missionService.getAllUserMission());
    }
}
