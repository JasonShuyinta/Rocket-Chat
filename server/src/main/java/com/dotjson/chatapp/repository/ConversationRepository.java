package com.dotjson.chatapp.repository;

import com.dotjson.chatapp.model.conversation.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConversationRepository extends MongoRepository<Conversation, String> {
}
