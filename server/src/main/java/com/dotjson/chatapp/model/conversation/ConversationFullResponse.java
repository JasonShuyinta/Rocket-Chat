package com.dotjson.chatapp.model.conversation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ConversationFullResponse {

    @JsonProperty("conversations")
    private List<ConversationResponse> conversationList;
}
