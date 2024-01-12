package com.animalSNS.animalSNS.image.entity;

import com.animalSNS.animalSNS.post.entity.Post;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long imageId;

    @Setter
    private String imageUri;

    @Setter
    private String imageName;

    @Setter
    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;
}
