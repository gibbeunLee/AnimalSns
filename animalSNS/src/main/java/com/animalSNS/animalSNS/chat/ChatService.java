package com.animalSNS.animalSNS.chat;

import com.animalSNS.animalSNS.exception.BusinessLogicException;
import com.animalSNS.animalSNS.exception.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;

    public ChatService(ChatRepository chatRepository, ChatRoomRepository chatRoomRepository) {
        this.chatRepository = chatRepository;
        this.chatRoomRepository = chatRoomRepository;
    }

    //메세지 생성
    public Message createMessage(Message message, String memberCode){
        //memberCode to memberId

        long chatRoomId = generateRoomId(message.getSender(), message.getRecipient());

        Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findById(chatRoomId);

        ChatRoom chatRoom = optionalChatRoom.orElseGet(() -> {
            return createChatRoom(chatRoomId);
        });

        Message createMessage =  Message.builder()
                .content(message.getContent())
                .sender(message.getSender())
                .recipient(message.getRecipient())
                .chatRoom(chatRoom)
                .build();

        return chatRepository.save(createMessage);
    }

    //멤버별 메세지 조회
    public List<Message> getMessagesByMember(String memberCode){
        //TODO: sender과 recipient 회원 찾기
        long roomId = generateRoomId(12345, 67890);
        Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findById(roomId);
        ChatRoom findChatRoom = optionalChatRoom.orElseThrow(() ->new BusinessLogicException(ExceptionCode.CHATROOM_NOT_FOUND));
        return chatRepository.findAllByChatRoom(findChatRoom);
    }

    //메세시 전체 조회
    public List<Message> getMessages(){
        return chatRepository.findAll();
    }

    //채팅방번호 생성
    private long generateRoomId(long sender, long recipient) {
        return sender ^ recipient;
    }

    //채팅방 생성
    private ChatRoom createChatRoom(long chatRoomId){
        return chatRoomRepository.save(new ChatRoom(chatRoomId));
    }


}
