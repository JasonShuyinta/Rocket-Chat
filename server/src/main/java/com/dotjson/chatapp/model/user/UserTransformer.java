package com.dotjson.chatapp.model.user;

import org.springframework.stereotype.Component;

@Component
public class UserTransformer {

    public User dtoToModel(UserRequest request) {
        User user = new User();
        user.setId(request.getId());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setImage(request.getImage());
        user.setConversationId(request.getConversationId());
        return user;
    }

    public UserResponse modelToDto(User user) {
        UserResponse dto = new UserResponse();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setImage(user.getImage());
        dto.setConversationId(user.getConversationId());
        return dto;
    }
}
