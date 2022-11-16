package com.dotjson.chatapp.model.conversation;

import com.dotjson.chatapp.model.user.User;
import com.dotjson.chatapp.model.user.UserResponse;
import com.dotjson.chatapp.model.user.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConversationTransformer {

    @Autowired
    UserTransformer userTransformer;

    public ConversationResponse modelToDto(Conversation model) {
        ConversationResponse dto = new ConversationResponse();
        dto.setId(model.getId());
        dto.setConversationName(model.getConversationName());
        dto.setConversationImage(model.getConversationImage());
        dto.setAdmin(userTransformer.modelToDto(model.getAdmin()));
        List<UserResponse> recipientsList = new ArrayList<>();
        for (User user:
             model.getRecipients()) {
            recipientsList.add(userTransformer.modelToDto(user));
        }
        dto.setRecipients(recipientsList);
        dto.setMessages(model.getMessages());
        return dto;
    }
}
