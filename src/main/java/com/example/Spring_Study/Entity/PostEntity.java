package com.example.Spring_Study.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private Long likes;
    private Long infinityLike;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
