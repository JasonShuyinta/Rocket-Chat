package com.dotjson.chatapp.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserResponse {

    @JsonProperty("id")
    private String id;
    @JsonProperty("username")
    private String username;
    @JsonProperty("image")
    private String image;
    @JsonProperty("conversations")
    private List<String> conversationId;

    @Override
    public String toString() {
        return "{id=" + id + ", username=" + username + ", conversationId=" + conversationId+"}";
    }
}
