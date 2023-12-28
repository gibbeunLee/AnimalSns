package com.animalSNS.animalSNS.post.dto;

import com.animalSNS.animalSNS.image.dto.ImageDto;
import com.animalSNS.animalSNS.post.entity.Post;
import com.sun.istack.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PostDto {
    @Getter
    public static class Post{
        private String content;
    }

    @Getter
    public static class Patch{
        private String content;
    }

    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response{
        private long postId;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<ImageDto> images;

        private long memberDetailId;
        private String profileImageLink;
        private String animalName;
        private String nickName;

        private int likeCnt;

        public Response(com.animalSNS.animalSNS.post.entity.Post post) {
        }

        public static Response from(com.animalSNS.animalSNS.post.entity.Post post){
            List<ImageDto> imageList = post.getImages().stream()
                    .map(image -> ImageDto.from(image))
                    .collect(Collectors.toList());

            return Response.builder()
                    .postId(post.getPostId())
                    .content(post.getContent())
                    .createdAt(post.getCreatedAt())
                    .updatedAt(post.getLastModifiedAt())
                    .images(imageList)
                    .memberDetailId(post.getMember().getMemberId())
                    //.profileImageLink(post.getMember().getProfileImageLink)
                    //.animalName(post.getMember().getAnimalName)
                    //.nickName(post.getMember().getNickName())
                    .build();
        }
    }
}
