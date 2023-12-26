package com.animalSNS.animalSNS.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class ChatService {

    private Map<String, ChatRoom> chatRooms = new ConcurrentHashMap<>();

    public ChatRoom getOrCreateRoom(String user1, String user2) {
        String roomId = generateRoomId(user1, user2);
        return chatRooms.computeIfAbsent(roomId, key -> new ChatRoom(roomId, user1, user2, LocalDateTime.now()));
    }

    public void updateLastActive(String roomId) {
        ChatRoom chatRoom = chatRooms.get(roomId);
        if (chatRoom != null) {
            chatRoom.setLastActive(LocalDateTime.now());
        }
    }

    public void removeInactiveRooms() {
        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);
        chatRooms.values().removeIf(chatRoom -> chatRoom.getLastActive().isBefore(tenMinutesAgo));
    }

    private String generateRoomId(String user1, String user2) {
        //TODO: roomId 생성
        return String.format("%s_%s", user1, user2);
    }
}
