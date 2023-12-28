package com.animalSNS.animalSNS.member.entity;

import com.animalSNS.animalSNS.audit.Auditable;
import com.animalSNS.animalSNS.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Member extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "member_role")
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    private String Email;


//    public enum AuthType {
//        GOOGLE,
//        KAKAO
//    }

    @OneToMany(mappedBy = "member")
    private List<Post> post;

    public enum MemberRole {
        MEMBER,
        ADMIN
    }
}
