package com.dotjson.chatapp.model.conversation;

import com.dotjson.chatapp.model.message.Message;
import com.dotjson.chatapp.model.user.UserRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ConversationRequest {

    @JsonProperty("id")
    private String id;
    @JsonProperty("conversationName")
    private String conversationName;
    @JsonProperty("conversationImage")
    private String conversationImage;
    @JsonProperty("admin")
    private UserRequest admin;
    @JsonProperty("recipients")
    private List<String> recipients;
    @JsonProperty("messages")
    private List<Message> messages;
}
