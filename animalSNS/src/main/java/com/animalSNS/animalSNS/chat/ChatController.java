package com.animalSNS.animalSNS.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@Slf4j
public class ChatController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload Chat chat) {
        String sender = chat.getSenderCode();
        String recipient = chat.getRecipientCode();

        ChatRoom room = chatService.getOrCreateRoom(sender, recipient);
        chatService.updateLastActive(room.getRoomId());

        messagingTemplate.convertAndSendToUser(recipient, "/queue/private", chat);
    }

    @MessageMapping("/chat.addUser")
    public void addUser(@Payload Chat chat, SimpMessageHeaderAccessor headerAccessor) {
        String sender = chat.getSenderCode();
        String recipient = chat.getRecipientCode();

        ChatRoom room = chatService.getOrCreateRoom(sender, recipient);
        chatService.updateLastActive(room.getRoomId());

        headerAccessor.getSessionAttributes().put("roomId", room.getRoomId());
        messagingTemplate.convertAndSendToUser(sender, "/queue/private", chat);
    }
}
