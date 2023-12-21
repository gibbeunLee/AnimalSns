package com.animalSNS.animalSNS.chat;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatDto {
    public enum messageType{
        ENTER, TALK
    }
    private messageType messageType;
    private Long chatRoomId;
    private Long senderCode;
    private String message;
}
