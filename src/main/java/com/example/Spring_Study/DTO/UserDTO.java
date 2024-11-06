package com.example.Spring_Study.DTO;

import com.example.Spring_Study.Entity.ChallengeEntity;
import com.example.Spring_Study.Entity.UserEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {
    private Long id;
    private String uid;
    private String password;
    private String name;
    private String nickname;
    private String email;
    private String phone;
    private int likeCount;
    private List<ChallengeDTO> challenge;

    public static UserDTO entityToDto(UserEntity userEntity){
        List<ChallengeDTO> challengeDTO = userEntity.getChallenge() != null
                ? userEntity.getChallenge().stream().map(ChallengeDTO::entityToDto).collect(Collectors.toList())
                : Collections.emptyList();
        return new UserDTO(
                userEntity.getId(),
                userEntity.getUid(),
                userEntity.getPassword(),
                userEntity.getName(),
                userEntity.getNickname(),
                userEntity.getEmail(),
                userEntity.getPhone(),
                userEntity.getLikeCount(),
                challengeDTO
        );
    }

    public UserEntity dtoToEntity(){
        UserEntity userEntity = new UserEntity(id, uid, password, name, nickname, email, phone, likeCount, new HashSet<>(), new ArrayList<>());
        List<ChallengeEntity> challengeEntity = challenge != null
                ? challenge.stream().map(ChallengeDTO::dtoToEntity).collect(Collectors.toList())
                : Collections.emptyList();
        userEntity.setChallenge(challengeEntity);
        return userEntity;
    }
}
