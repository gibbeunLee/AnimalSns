package com.animalSNS.animalSNS.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {


    // 소켓 연결 확인
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("{} 연결됨", session.getId());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // 클라이언트로부터 메시지를 받았을 때 실행되는 로직
        // 메시지를 받아 처리하는 로직을 작성
        String payload = message.getPayload().toString();
        session.sendMessage(new TextMessage("Received: " + payload));
    }

    // 소켓 종료 확인
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("{} 연결 끊김", session.getId());
    }
}
