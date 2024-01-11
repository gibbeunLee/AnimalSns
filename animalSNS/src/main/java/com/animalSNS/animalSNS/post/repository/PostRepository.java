package com.animalSNS.animalSNS.post.repository;

import com.animalSNS.animalSNS.post.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findById(long postId);

    List<Post> findAllByOrderByPostIdDesc(Pageable pageable);

    List<Post> findByPostIdLessThanOrderByPostIdDesc(Long postId, Pageable pageable);

    List<Post> findByMemberMemberIdOrderByPostIdDesc(Long memberId, Pageable pageable);

    List<Post> findByMemberMemberIdAndPostIdLessThanOrderByPostIdDesc(long postId, Long memberId, Pageable pageable);
}
