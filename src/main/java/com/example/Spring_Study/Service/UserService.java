package com.example.Spring_Study.Service;

import com.example.Spring_Study.Entity.UserEntity;
import com.example.Spring_Study.DTO.UserDTO;
import com.example.Spring_Study.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    // 회원 가입
    public UserDTO createUser(UserDTO userDTO) {
        UserEntity userEntity = userDTO.dtoToEntity();
        UserEntity savedUser = userRepository.save(userEntity);
        logger.info("회원가입 완료!");
        return UserDTO.entityToDto(savedUser);
    }

    // 전체 회원 조회
    public List<UserDTO> getAllUsers() {
        List<UserDTO> userDTO = userRepository.findAll().stream()
                .map(UserDTO::entityToDto)
                .collect(Collectors.toList());
        logger.info(userDTO.size() + "명 사용자 전체 조회 완료!");
        return userDTO;
    }

    // id 회원 조회
    public UserDTO findById(Long id){
        UserEntity userEntity = userRepository.findById(id).orElseThrow();
        logger.info(id + "번 유저 조회 완료!");
        return UserDTO.entityToDto(userEntity);
    }

    // 회원 수정
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow();
        userEntity.setUid(userDTO.getUid());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setName(userDTO.getName());
        userEntity.setNickname(userDTO.getNickname());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPhone(userDTO.getPhone());
        UserEntity updatedUser = userRepository.save(userEntity);
        logger.info(id + "번 사용자 정보 업데이트 완료!");
        return UserDTO.entityToDto(updatedUser);
    }

    // 회원 삭제
    public UserDTO deleteUser(Long id){
        UserEntity userEntity = userRepository.findById(id).orElseThrow();
        userRepository.delete(userEntity);
        logger.info(id + "번 사용자 탈퇴 완료! ");
        return UserDTO.entityToDto(userEntity);
    }
}
