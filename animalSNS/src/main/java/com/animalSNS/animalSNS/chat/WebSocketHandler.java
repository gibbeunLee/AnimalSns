package com.animalSNS.animalSNS.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper mapper;

    private final Set<WebSocketSession> sessions = new HashSet<>();

    private final Map<Long, Set<WebSocketSession>> chatRoomSessionMap = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("{} 연결됨", session.getId());
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);

        ChatDto chatDto = mapper.readValue(payload, ChatDto.class);
        log.info("session {}", chatDto.toString());

        Long chatRoomId = chatDto.getChatRoomId();
        if(!chatRoomSessionMap.containsKey(chatRoomId)){
            chatRoomSessionMap.put(chatRoomId,new HashSet<>());
        }
        Set<WebSocketSession> chatRoomSession = chatRoomSessionMap.get(chatRoomId);


        if (chatDto.getMessageType().equals(ChatDto.messageType.ENTER)) {
            chatRoomSession.add(session);
        }
        if (chatRoomSession.size()>=3) {
            removeClosedSession(chatRoomSession);
        }
        sendMessageToChatRoom(chatDto, chatRoomSession);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("{} 연결 끊김", session.getId());
        sessions.remove(session);
    }

    private void removeClosedSession(Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.removeIf(session -> !sessions.contains(session));
    }

    private void sendMessageToChatRoom(ChatDto chatDto, Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.parallelStream().forEach(session -> sendMessage(session, chatDto));
    }


    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
