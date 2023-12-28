package com.animalSNS.animalSNS.post.entity;

import com.animalSNS.animalSNS.audit.Auditable;
import com.animalSNS.animalSNS.image.entity.Image;
import com.animalSNS.animalSNS.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class Post extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postId;

    @Setter
    private String content;

    @Setter
    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @Setter
    @OneToMany(mappedBy = "post")
    private List<Image> images;
}
