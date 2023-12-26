package com.animalSNS.animalSNS.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
public class ChatRoom {
    private String roomId;
    private String user1;
    private String user2;
    private LocalDateTime lastActive;
}
