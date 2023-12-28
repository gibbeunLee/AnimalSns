package com.animalSNS.animalSNS.image.repository;

import com.animalSNS.animalSNS.image.entity.Image;
import com.animalSNS.animalSNS.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findByPostId(long postId);
    void deleteByPostId(long postId);
}
