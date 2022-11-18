package com.dotjson.chatapp.controller;

import com.dotjson.chatapp.ChatAppBackendApplicationTests;
import com.dotjson.chatapp.model.conversation.ConversationRequest;
import com.dotjson.chatapp.model.message.Message;
import com.dotjson.chatapp.model.user.UserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

class ConversationControllerTest extends ChatAppBackendApplicationTests {

    @Test
    void createConversation() throws Exception {
        ConversationRequest conversationRequest = new ConversationRequest();
        UserRequest admin = new UserRequest();
        admin.setId(USER_ID);
        conversationRequest.setAdmin(admin);
        List<String> recipients = new ArrayList<>();
        recipients.add("63778b21cf28f62396de5a50");
        recipients.add("63778be752c9427e1ffb375b");
        conversationRequest.setRecipients(recipients);
        conversationRequest.setConversationName("conversationName");
        conversationRequest.setConversationImage("conversationImage");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post(CREATE_CONVERSATION_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(conversationRequest)))
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void getConversationById() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(GET_CONVERSATION_BY_ID_ENDPOINT, CONVERSATION_ID))
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void addMessage() throws Exception {
        Message message = new Message();
        message.setAuthorId(USER_ID);
        message.setText("Some text");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post(ADD_MESSAGE, CONVERSATION_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(message)))
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void getAllConversations() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(GET_ALL_CONVERSATIONS, USER_ID))
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

}
