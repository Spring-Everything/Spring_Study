package com.example.Spring_Study.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

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
    private int likes = 0;
    private int infinityLike = 0;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
