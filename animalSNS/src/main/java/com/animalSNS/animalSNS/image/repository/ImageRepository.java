package com.animalSNS.animalSNS.image.repository;

import com.animalSNS.animalSNS.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByPostPostId(long postId);
    void deleteById(long postId);
}
