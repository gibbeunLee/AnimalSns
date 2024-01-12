package com.animalSNS.animalSNS.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ChatDto {

    //TODO: sender jwt를 이용해 회원 찾기
    @Getter
    @Builder
    public static class ChatPostDto{
        private String content;
        private long sender;
        private long recipient;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class ChatResponseDto{
        private String content;
        private long sender;
        private long recipient;
    }

}
