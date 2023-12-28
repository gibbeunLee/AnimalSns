package com.animalSNS.animalSNS.image.service;

import com.animalSNS.animalSNS.image.entity.Image;
import com.animalSNS.animalSNS.image.repository.ImageRepository;
import com.animalSNS.animalSNS.post.entity.Post;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ImageService {
    public static final String IMAGE_SAVE_URL = "";
    public static final String SEPERATOR  = "_";
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<String> findImagesByPostId(long postId) {
        return imageRepository.findByPostId(postId).stream()
                .map(image -> image.getImageUri())
                .collect(Collectors.toList());
    }

    public List<String> saveImages(Post post, List<MultipartFile> images) {
        return images.stream()
                .map(img -> {
                    Image image = new Image();
                    UUID uuid = UUID.randomUUID();
                    String type = img.getContentType();

                    String imageSavePath = IMAGE_SAVE_URL + uuid.toString() + SEPERATOR + img.getOriginalFilename();

                    image.setImageUri(imageSavePath);
                    image.setPost(post);

                    imageRepository.save(image);
                    return imageSavePath;
                })
                .collect(Collectors.toList());
    }

    public void deleteImageByPostId(long postId) {
        imageRepository.deleteByPostId(postId);
    }
}
