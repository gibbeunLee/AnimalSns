package com.animalSNS.animalSNS.image.dto;

import com.animalSNS.animalSNS.image.entity.Image;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ImageDto {
    private String imageUri;
    public static ImageDto from(Image images) {
        return ImageDto.builder()
                .imageUri(images.getImageUri())
                .build();
    }
}
