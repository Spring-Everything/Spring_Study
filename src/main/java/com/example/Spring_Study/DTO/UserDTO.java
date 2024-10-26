package com.example.Spring_Study.DTO;

import com.example.Spring_Study.Entity.UserEntity;
import lombok.*;

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

    public static UserDTO entityToDto(UserEntity userEntity){
        return new UserDTO(
                userEntity.getId(),
                userEntity.getUid(),
                userEntity.getPassword(),
                userEntity.getName(),
                userEntity.getNickname(),
                userEntity.getEmail(),
                userEntity.getPhone()
        );
    }

    public UserEntity dtoToEntity(){
        return new UserEntity(id, uid, password, name, nickname, email, phone);
    }
}
