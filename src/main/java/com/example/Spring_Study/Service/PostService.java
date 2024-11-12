package com.example.Spring_Study.Service;

import com.example.Spring_Study.DTO.PostDTO;
import com.example.Spring_Study.Entity.PostEntity;
import com.example.Spring_Study.Entity.UserEntity;
import com.example.Spring_Study.Repository.PostRepository;
import com.example.Spring_Study.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ChallengeService challengeService;
    private final MissionService missionService;

    // 게시글 작성
    public PostDTO createPost(Long userId, PostDTO postDTO) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow();
        PostEntity postEntity = postDTO.dtoToEntity(userEntity);
        PostEntity savedPost = postRepository.save(postEntity);
        logger.info("게시글 생성 완료! 게시글 생성자 : " + userId + "번 유저");
        return PostDTO.entityToDto(savedPost);
    }

    // 해당 유저 작성 게시글 전체 조회
    public List<PostDTO> getPostsByUserId(Long userId) {
        return postRepository.findPostByUserId(userId)
                .stream()
                .map(PostDTO::entityToDto)
                .collect(Collectors.toList());
    }

    // 게시글 전체 조회
    public List<PostDTO> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(PostDTO::entityToDto)
                .collect(Collectors.toList());
    }

    // 게시글 제목으로 검색
    public List<PostDTO> findPostByTitle(String title) {
        return postRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(PostDTO::entityToDto)
                .collect(Collectors.toList());
    }

    // 게시글 내용으로 검색
    public List<PostDTO> findPostByContent(String content) {
        return postRepository.findByContentContainingIgnoreCase(content)
                .stream()
                .map(PostDTO::entityToDto)
                .collect(Collectors.toList());
    }

    // 게시글 수정
    public PostDTO updatePost(Long userId, Long postId, PostDTO postDTO) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow();
        PostEntity postEntity = postRepository.findById(postId).orElseThrow();
        if(postEntity.getUser().getId().equals(userEntity.getId())){
            postEntity.setTitle(postDTO.getTitle());
            postEntity.setContent(postDTO.getContent());
            postEntity = postRepository.save(postEntity);
        } else {
            throw new RuntimeException("해당 유저의 게시글이 아니여서 수정이 불가합니다");
        }
        logger.info("게시글 수정 완료!");
        return PostDTO.entityToDto(postEntity);
    }

    // 게시글 삭제
    public PostDTO deletePost(Long postId) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow();
        postRepository.delete(postEntity);
        logger.info("게시글 삭제 완료!");
        return PostDTO.entityToDto(postEntity);
    }

    // 게시글 좋아요
    public PostDTO likePost(Long userId, Long postId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow();
        PostEntity postEntity = postRepository.findById(postId).orElseThrow();
        if (userEntity.getLikedPosts().contains(postId)) {
            userEntity.getLikedPosts().remove(postId);
            postEntity.setLikes(postEntity.getLikes() - 1);
            userEntity.setLikeCount(userEntity.getLikeCount() - 1);
        } else {
            userEntity.getLikedPosts().add(postId);
            postEntity.setLikes(postEntity.getLikes() + 1);
            userEntity.setLikeCount(userEntity.getLikeCount() + 1);
        }
        challengeService.checkAndUpdateChallengeStatus(userEntity.getId());
        missionService.checkAndUpdateMissionStatus(userEntity.getId());
        postRepository.save(postEntity);
        userRepository.save(userEntity);
        logger.info(userId + "번 유저의 좋아요 상태 변경완료!");
        return PostDTO.entityToDto(postEntity);
    }

    // 게시글 무한 좋아요
    public PostDTO infinityLikePost(Long userId, Long postId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow();
        PostEntity postEntity = postRepository.findById(postId).orElseThrow();
        postEntity.setInfinityLike(postEntity.getInfinityLike() + 1);
        userEntity.setLikeCount(userEntity.getLikeCount() + 1);
        challengeService.checkAndUpdateChallengeStatus(userEntity.getId());
        missionService.checkAndUpdateMissionStatus(userEntity.getId());
        userRepository.save(userEntity);
        postRepository.save(postEntity);
        logger.info("해당 게시글 좋아요 추가 완료!");
        return PostDTO.entityToDto(postEntity);
    }
}
