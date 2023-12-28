package com.animalSNS.animalSNS.post.service;

import com.animalSNS.animalSNS.exception.BusinessLogicException;
import com.animalSNS.animalSNS.exception.ExceptionCode;
import com.animalSNS.animalSNS.image.service.ImageService;
import com.animalSNS.animalSNS.member.entity.Member;
import com.animalSNS.animalSNS.post.dto.PostDto;
import com.animalSNS.animalSNS.post.entity.Post;
import com.animalSNS.animalSNS.post.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class PostService {
    private final PostRepository postRepository;
    private final ImageService imageService;

    public PostService(PostRepository postRepository, ImageService imageService) {
        this.postRepository = postRepository;
        this.imageService = imageService;
    }

    public PostDto.Response createPost(Post post, List<MultipartFile> images, long memberId) throws NullPointerException {
        Member member = new Member(); //memberService.findMemberById(memberId);
        post.setMember(member);

        Post savePost = postRepository.save(post);

        imageService.saveImages(savePost, images);

        Post findPost = postRepository.findByPostId(savePost.getPostId())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));;

        PostDto.Response response = PostDto.Response.from(findPost);
        return response;
    }

    @Transactional
    public PostDto.Response updatePost(long memberId, long postId, Post post, List<MultipartFile> images) {
        Post findPost = findVerifidePost(postId);
        validateWriter(memberId, findPost);

        findPost.setContent(post.getContent());
        imageService.deleteImageByPostId(postId);
        imageService.saveImages(findPost, images);

        Post savePost = postRepository.save(findPost);

        PostDto.Response response = PostDto.Response.from(savePost);
        return response;
    }

    public void deletePost(long postId, long memberId) {
        Post findPost = findVerifidePost(postId);

        validateWriter(memberId, findPost);

        imageService.deleteImageByPostId(postId);
        postRepository.delete(findPost);
    }

    public Post findVerifidePost(long postId) {
        Post findPost = postRepository.findByPostId(postId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));
        return findPost;
    }

    public void validateWriter(long memberId, Post post){
        if(post.getMember().getMemberId() != memberId) throw new BusinessLogicException(ExceptionCode.UNMATCHED_WRITER);
    }

    public List<PostDto.Response> findPostByPage(long cursor, Pageable pageable) {
        return getPostList(cursor, pageable)
                .stream().map(PostDto.Response::new).collect(Collectors.toList());
    }
    // 페이징 처리를 위한 메서드
    private List<Post> getPostList(Long postId, Pageable page) {
        return postId.equals(0L)
                ? postRepository.findByOrderByPostIdDesc(page)
                : postRepository.findByPostIdLessThanOrderByPostIdDesc(postId, page);
    }

    public List<PostDto.Response> findFollowingFostByPage(long cursor, long memberId, Pageable pageable) {
        //long followingMemberId = memberService.findFollowingMemberId(memberId);
        return getFollowingPostList(cursor, memberId, pageable)
                .stream().map(PostDto.Response::new).collect(Collectors.toList());
    }
    private List<Post> getFollowingPostList(Long postId, long followingMemberId, Pageable page) {
        return postId.equals(0L)
                ? postRepository.findByMemberIdOrderByPostIdDesc(followingMemberId, page)
                : postRepository.findByPostIdAndMemberIdLessThanOrderByPostIdDesc(postId, followingMemberId, page);
    }
}
