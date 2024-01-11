package com.animalSNS.animalSNS.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.animalSNS.animalSNS.exception.BusinessLogicException;
import com.animalSNS.animalSNS.exception.ExceptionCode;
import com.animalSNS.animalSNS.image.entity.Image;
import com.animalSNS.animalSNS.image.repository.ImageRepository;
import com.animalSNS.animalSNS.post.entity.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ImageService {
    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;
    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    private final AmazonS3 amazonS3;

    public static final String IMAGE_SAVE_URL = "";
    public static final String SEPERATOR  = "_";
    private final ImageRepository imageRepository;

    public ImageService(AmazonS3 amazonS3, ImageRepository imageRepository) {
        this.amazonS3 = amazonS3;
        this.imageRepository = imageRepository;
    }

    public List<String> findImagesByPostId(long postId) {
        return imageRepository.findById(postId).stream()
                .map(image -> image.getImageUri())
                .collect(Collectors.toList());
    }

    public List<String> saveImages(Post post, List<MultipartFile> images) {
        return images.stream()
                .map(img -> {
                    UUID uuid = UUID.randomUUID();
                    String type = img.getContentType();
                    String name = uuid.toString() + SEPERATOR + img.getOriginalFilename();


                    ObjectMetadata metadata = new ObjectMetadata();
                    metadata.setContentType(type);
                    try {
                        PutObjectResult putObjectResult = amazonS3.putObject(new PutObjectRequest(
                                bucketName, name, img.getInputStream(), metadata
                        ).withCannedAcl(CannedAccessControlList.PublicRead));

                    } catch (IOException e) {
                        throw new BusinessLogicException(ExceptionCode.IMAGE_SAVE_FAILED); //커스텀 예외 던짐.
                    }

                    String imageSavePath = amazonS3.getUrl(bucketName, name).toString(); //데이터베이스에 저장할 이미지가 저장된 주소
                    //IMAGE_SAVE_URL + name;

                    Image image = new Image();
                    image.setImageUri(imageSavePath);
                    image.setPost(post);

                    imageRepository.save(image);
                    return imageSavePath;
                })
                .collect(Collectors.toList());
    }

    public List<String> saveImagesTest(List<MultipartFile> images) {
        log.info("Start");
        return images.stream()
                .map(img -> {
                    UUID uuid = UUID.randomUUID();
                    String type = img.getContentType();
                    String name = uuid.toString() + SEPERATOR + img.getOriginalFilename();


                    ObjectMetadata metadata = new ObjectMetadata();
                    metadata.setContentType(type);
                    try {
                        PutObjectResult putObjectResult = amazonS3.putObject(new PutObjectRequest(
                                bucketName, name, img.getInputStream(), metadata
                        ).withCannedAcl(CannedAccessControlList.PublicRead));

                    } catch (IOException e) {
                        throw new BusinessLogicException(ExceptionCode.IMAGE_SAVE_FAILED); //커스텀 예외 던짐.
                    }

                    String imageSavePath = amazonS3.getUrl(bucketName, name).toString(); //데이터베이스에 저장할 이미지가 저장된 주소
                    //IMAGE_SAVE_URL + name;

                    Image image = new Image();
                    image.setImageUri(imageSavePath);
                    log.info(imageSavePath);
                    imageRepository.save(image);
                    return imageSavePath;
                })
                .collect(Collectors.toList());
    }

    public void deleteImageByPostId(long postId) {
        imageRepository.deleteById(postId);
    }
}
