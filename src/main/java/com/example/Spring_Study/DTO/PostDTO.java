package com.example.Spring_Study.DTO;

import com.example.Spring_Study.Entity.PostEntity;
import com.example.Spring_Study.Entity.UserEntity;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private int likes;
    private int infinityLike;
    private LocalDate date;
    private Long userId;
    private UserDTO user;

    public static PostDTO entityToDto(PostEntity postEntity){
        return new PostDTO(
                postEntity.getId(),
                postEntity.getTitle(),
                postEntity.getContent(),
                postEntity.getLikes(),
                postEntity.getInfinityLike(),
                postEntity.getDate(),
                postEntity.getUser().getId(),
                UserDTO.entityToDto(postEntity.getUser())
        );
    }

    public PostEntity dtoToEntity(UserEntity user){
        return new PostEntity(id, title, content, likes, infinityLike, date, user);
    }
}
