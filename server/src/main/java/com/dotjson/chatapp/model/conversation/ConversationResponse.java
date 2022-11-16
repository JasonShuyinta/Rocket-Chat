package com.dotjson.chatapp.model.conversation;

import com.dotjson.chatapp.model.message.Message;
import com.dotjson.chatapp.model.user.UserResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ConversationResponse {

    @JsonProperty("id")
    private String id;
    @JsonProperty("conversationName")
    private String conversationName;
    @JsonProperty("conversationImage")
    private String conversationImage;
    @JsonProperty("admin")
    private UserResponse admin;
    @JsonProperty("recipients")
    private List<UserResponse> recipients;
    @JsonProperty("messages")
    private List<Message> messages;

    @Override
    public String toString() {
        return "{id= " + id +", conversationName="+conversationName+", admin="+admin+", recipients="+recipients
                +", messages="+messages+"}";
    }

}
