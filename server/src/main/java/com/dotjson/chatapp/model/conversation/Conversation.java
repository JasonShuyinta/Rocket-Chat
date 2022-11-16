package com.dotjson.chatapp.model.conversation;

import com.dotjson.chatapp.model.message.Message;
import com.dotjson.chatapp.model.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@Document
public class Conversation {

    @Id
    private String id;
    private String conversationName;
    private String conversationImage;
    private User admin;
    private List<User> recipients;
    private List<Message> messages;
}
