package com.animalSNS.animalSNS.chat;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.Session;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;
    private final ChatMapper mapper;
    private final ChatWebSocketHandler chatWebSocketHandler;

    public ChatController(ChatService chatService, ChatMapper mapper, ChatWebSocketHandler webSocketHandler) {
        this.chatService = chatService;
        this.mapper = mapper;
        this.chatWebSocketHandler = webSocketHandler;
    }

    @Autowired
    private WebSocketHandler webSocketHandler;

    //메세지 생성
    @PostMapping()
    public ResponseEntity<?> sendToMessage(@RequestBody ChatDto.ChatPostDto chatDto,
                                           @RequestParam("memberCode") String memberCode){

        Message message = chatService.createMessage(mapper.chatDtoToMessage(chatDto), memberCode);


        return new ResponseEntity<>(mapper.messageToChatDto(message) , HttpStatus.CREATED);
    }

    //멤버별 메세지 조회
    @GetMapping("/{memberCode}")
    public ResponseEntity<?> getMessages(@PathVariable("memberCode") String memberCode){

        List<Message> messageList = chatService.getMessagesByMember(memberCode);
        return new ResponseEntity<>(mapper.messageListToChatDtoList(messageList) , HttpStatus.OK);
    }

    //전체 메세지 조회
    @GetMapping()
    public ResponseEntity<?> getRoomList(){
        List<Message> messageList = chatService.getMessages();
        return new ResponseEntity<>(mapper.messageListToChatDtoList(messageList) , HttpStatus.OK);
    }
}
