package com.animalSNS.animalSNS.chat;

import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@NoArgsConstructor
public class ChatRoom {

    @Id
    private long chatRoomId;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<Message> messageList;

    public ChatRoom(long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

}
