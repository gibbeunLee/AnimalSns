package com.animalSNS.animalSNS.chat;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChatMapper {

    default Message chatDtoToMessage(ChatDto.ChatPostDto chatDto){
        Message message = Message.builder()
                .content(chatDto.getContent())
                .sender(chatDto.getSender())
                .recipient(chatDto.getRecipient())
                .build();

        return message;
    }

    default ChatDto.ChatResponseDto messageToChatDto(Message message){
        ChatDto.ChatResponseDto chatDto = ChatDto.ChatResponseDto.builder()
                                            .content(message.getContent())
                                            .recipient(message.getRecipient())
                                            .sender(message.getSender())
                                            .build();

        return chatDto;
    }

    default List<ChatDto.ChatResponseDto> messageListToChatDtoList(List<Message> messageList){
        return messageList.stream()
                .map(this::messageToChatDto)
                .collect(Collectors.toList());
    }
}
