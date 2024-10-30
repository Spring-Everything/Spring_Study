package com.example.Spring_Study.Controller;

import com.example.Spring_Study.DTO.PostDTO;
import com.example.Spring_Study.Repository.UserRepository;
import com.example.Spring_Study.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;

    // 게시글 작성
    @PostMapping("/{userId}")
    public ResponseEntity<PostDTO> createPost(@PathVariable Long userId, @RequestBody PostDTO postDTO) {
        return ResponseEntity.ok(postService.createPost(userId, postDTO));
    }

    // 해당 유저 작성 게시글 전체 조회
    @GetMapping("/{userId}")
    public ResponseEntity<List<PostDTO>> getPostsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.getPostsByUserId(userId));
    }

    // 게시글 전체 조회
    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    // 게시글 수정
    @PutMapping("/{userId}/{postId}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long userId, @PathVariable Long postId, @RequestBody PostDTO postDTO) {
        return ResponseEntity.ok(postService.updatePost(userId, postId, postDTO));
    }

    // 게시글 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<PostDTO> deletePost(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.deletePost(userId));
    }

    // 게시글 좋아요
    @PostMapping("/like/{userId}/{postId}")
    public ResponseEntity<PostDTO> likePost(@PathVariable Long userId ,@PathVariable Long postId) {
        return ResponseEntity.ok(postService.likePost(userId, postId));
    }

    // 게시글 무한 좋아요
    @PostMapping("/infinityLike/{userId}/{postId}")
    public ResponseEntity<PostDTO> infinityLikePost(@PathVariable Long userId ,@PathVariable Long postId) {
        return ResponseEntity.ok(postService.infinityLikePost(userId, postId));
    }
}
