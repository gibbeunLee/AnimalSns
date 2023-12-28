package com.animalSNS.animalSNS.post.repository;

import com.animalSNS.animalSNS.post.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Optional<Post> findByPostId(long postId);

    @Query("select p from Post p order by p.id desc")
    List<Post> findByOrderByPostIdDesc(Pageable pageable);

    List<Post> findByPostIdLessThanOrderByPostIdDesc(Long postId, Pageable pageable);

    List<Post> findByMemberIdOrderByPostIdDesc(long memberId, Pageable pageable);

    List<Post> findByPostIdAndMemberIdLessThanOrderByPostIdDesc(long postId, long memberId, Pageable pageable);
}
