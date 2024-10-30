package com.example.Spring_Study.DTO;

import com.example.Spring_Study.Entity.PostEntity;
import com.example.Spring_Study.Entity.UserEntity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private Long likes;
    private Long infinityLike;
    private Long userId;
    private UserDTO user;

    public static PostDTO entityToDto(PostEntity postEntity){
        return new PostDTO(
                postEntity.getId(),
                postEntity.getTitle(),
                postEntity.getContent(),
                postEntity.getLikes(),
                postEntity.getInfinityLike(),
                postEntity.getUser().getId(),
                UserDTO.entityToDto(postEntity.getUser())
        );
    }

    public PostEntity dtoToEntity(UserEntity user){
        return new PostEntity(id, title, content, likes, infinityLike, user);
    }
}
