package com.dotjson.chatapp.repository;

import com.dotjson.chatapp.model.message.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String> {
}
