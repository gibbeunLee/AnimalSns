package com.animalSNS.animalSNS.post.controller;

import com.animalSNS.animalSNS.auth.jwt.JwtTokenizer;
import com.animalSNS.animalSNS.post.dto.PostDto;
import com.animalSNS.animalSNS.post.entity.Post;
import com.animalSNS.animalSNS.post.mapper.PostMapper;
import com.animalSNS.animalSNS.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
// post 생성, 수정, 삭제, 조회(댓글, 이미지, 좋아요 함께 조회)
// 팔로잉 게시글 조회는 어디서? follow 테이블?
@RestController
@RequestMapping("posts")
@Validated
@Slf4j
public class PostController {
    private final PostService postService;
    private final PostMapper mapper;
    private final JwtTokenizer jwtTokenizer;
    private static final int PAGE_DEFAULT_SIZE = 10;

    public PostController(PostService postService, PostMapper mapper, JwtTokenizer jwtTokenizer) {
        this.postService = postService;
        this.mapper = mapper;
        this.jwtTokenizer = jwtTokenizer;
    }

    @GetMapping // 게시글 전체 조회 - 게시글 어떤 순서로 조회할지 고민해야함
    public ResponseEntity getPosts(final Pageable pageSize, long cursor) {
        List<PostDto.Response> postDetailResponseDtoList = postService.findPostByPage(cursor, PageRequest.of(0, PAGE_DEFAULT_SIZE));
        return new ResponseEntity<>(postDetailResponseDtoList, HttpStatus.OK);
        /*
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt"); // challengeId를 내림차순으로 정렬하는 Sort 객체 생성
        Pageable pageable = PageRequest.of(pageSize.getPageNumber(), pageSize.getPageSize(), sort);
        Page<Post> postPage = null; //= postService.getPostsPage(pageable);
        List<PostDto.Response> response = null;// = postService.getPosts(pageable);
        return new ResponseEntity<>(new MultiResponseDto<>(response, postPage), HttpStatus.OK);

         */
    }

    @GetMapping("/follow") // 팔로잉 게시글 조회
    public ResponseEntity getFollowingPost(final Pageable pageSize, long cursor) {
        List<PostDto.Response> postDetailResponseDtoList = postService.findPostByPage(cursor, PageRequest.of(0, PAGE_DEFAULT_SIZE));
        return new ResponseEntity<>(postDetailResponseDtoList, HttpStatus.OK);

        /*
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt"); // challengeId를 내림차순으로 정렬하는 Sort 객체 생성
        Pageable pageable = PageRequest.of(pageSize.getPageNumber(), pageSize.getPageSize(), sort);
        Page<Post> postPage = null; //= postService.getPostsPage(pageable);
        List<PostDto.Response> response = null;// = postService.getPosts(pageable);
        return new ResponseEntity<>(new MultiResponseDto<>(response, postPage), HttpStatus.OK);

         */
    }

    @PostMapping() // v게시글 생성
    public ResponseEntity createPost(@RequestPart PostDto.Post requestBody,
                                     @RequestPart List<MultipartFile> images,
                                     @RequestHeader(value = "Authorization") String token){
        Post post = mapper.postPostDtoToPost(requestBody);
        long memberId = 1L;//jwtTokenizer.getMemberId(token);
        PostDto.Response response = postService.createPost(post, images, memberId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PatchMapping("/{postId}") // v게시글 수정
    public ResponseEntity patchPost(@PathVariable("postId") long postId,
                                    @RequestPart(required = false) PostDto.Post requestBody,
                                    @RequestPart(required = false) List<MultipartFile> images,
                                    @RequestHeader(value = "Authorization", required = false) String token) {
        Post post = mapper.postPostDtoToPost(requestBody);
        long memberId = 1L; // jwtTokenizer.getMemberId(token);
        postService.updatePost(memberId, postId, post, images);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{postId}") // v게시글 삭제
    public ResponseEntity deletePost(@PathVariable("postId") long postId,
                                     @RequestHeader(value = "Authorization") String token) {
        long memberId = 1L; // jwtTokenizer.getMemberId(token);
        postService.deletePost(postId, memberId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
