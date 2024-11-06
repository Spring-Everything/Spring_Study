package com.example.Spring_Study.Controller;

import com.example.Spring_Study.DTO.MissionDTO;
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
    @GetMapping
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
}
