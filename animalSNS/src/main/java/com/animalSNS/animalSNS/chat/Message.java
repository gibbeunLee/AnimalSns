package com.animalSNS.animalSNS.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long messageId;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false)
    private long sender;

    @Column(nullable = false)
    private long recipient;

    @ManyToOne
    @JoinColumn(name = "chatRoomId")
    private ChatRoom chatRoom;

}
