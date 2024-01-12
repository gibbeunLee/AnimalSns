package com.animalSNS.animalSNS.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByChatRoom(ChatRoom chatRoom);

}
