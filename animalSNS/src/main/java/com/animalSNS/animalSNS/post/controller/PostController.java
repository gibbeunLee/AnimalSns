package com.animalSNS.animalSNS.post.controller;

import com.animalSNS.animalSNS.auth.jwt.JwtTokenizer;
import com.animalSNS.animalSNS.post.dto.PostDto;
import com.animalSNS.animalSNS.post.entity.Post;
import com.animalSNS.animalSNS.post.mapper.PostMapper;
import com.animalSNS.animalSNS.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/posts")
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

    @GetMapping
    public ResponseEntity getPosts(final Pageable pageSize, long cursor) {
        List<PostDto.Response> postDetailResponseDtoList = postService.findPostByPage(cursor, PageRequest.of(0, PAGE_DEFAULT_SIZE));
        return new ResponseEntity<>(postDetailResponseDtoList, HttpStatus.OK);

    }

    @GetMapping("/follow")
    public ResponseEntity getFollowingPost(final Pageable pageSize, long cursor) {
        List<PostDto.Response> postDetailResponseDtoList = postService.findPostByPage(cursor, PageRequest.of(0, PAGE_DEFAULT_SIZE));
        return new ResponseEntity<>(postDetailResponseDtoList, HttpStatus.OK);

    }

    @PostMapping()
    public ResponseEntity createPost(@RequestPart PostDto.Post requestBody,
                                     @RequestPart List<MultipartFile> images,
                                     @RequestHeader(value = "Authorization") String token){
        Post post = mapper.postPostDtoToPost(requestBody);
        long memberId = 1L;//jwtTokenizer.getMemberId(token);
        PostDto.Response response = postService.createPost(post, images, memberId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PatchMapping("/{postId}")
    public ResponseEntity patchPost(@PathVariable("postId") long postId,
                                    @RequestPart(required = false) PostDto.Post requestBody,
                                    @RequestPart(required = false) List<MultipartFile> images,
                                    @RequestHeader(value = "Authorization", required = false) String token) {
        Post post = mapper.postPostDtoToPost(requestBody);
        long memberId = 1L; // jwtTokenizer.getMemberId(token);
        postService.updatePost(memberId, postId, post, images);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity deletePost(@PathVariable("postId") long postId,
                                     @RequestHeader(value = "Authorization") String token) {
        long memberId = 1L; // jwtTokenizer.getMemberId(token);
        postService.deletePost(postId, memberId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
