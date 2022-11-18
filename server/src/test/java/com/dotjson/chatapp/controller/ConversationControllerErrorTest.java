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

class ConversationControllerErrorTest extends ChatAppBackendApplicationTests {

    @Test
    void createConversationAdminNotPresentTest() throws Exception {
        ConversationRequest conversationRequest = new ConversationRequest();
        UserRequest admin = new UserRequest();
        admin.setId("1");
        conversationRequest.setAdmin(admin);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post(CREATE_CONVERSATION_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(conversationRequest)))
                .andReturn();
        Assertions.assertEquals(404, result.getResponse().getStatus());
    }

    @Test
    void createConversationRecipientNotPresentTest() throws Exception {
        ConversationRequest conversationRequest = new ConversationRequest();
        UserRequest admin = new UserRequest();
        admin.setId(USER_ID);
        conversationRequest.setAdmin(admin);
        List<String> recipients = new ArrayList<>();
        recipients.add("1");
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
        Assertions.assertEquals(404, result.getResponse().getStatus());
    }

    @Test
    void getConversationByIdNotPresent() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(GET_CONVERSATION_BY_ID_ENDPOINT, "1"))
                .andReturn();
        Assertions.assertEquals(404, result.getResponse().getStatus());
    }

    @Test
    void addMessageConversationNotPresentError() throws Exception {
        Message message = new Message();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post(ADD_MESSAGE, "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(message)))
                .andReturn();
        Assertions.assertEquals(404, result.getResponse().getStatus());
    }

    @Test
    void addMessageAuthorIsNotPresent() throws Exception {
        Message message = new Message();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post(ADD_MESSAGE, CONVERSATION_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(message)))
                .andReturn();
        Assertions.assertEquals(404, result.getResponse().getStatus());
    }

    @Test
    void addMessageTextIsNotPresent() throws Exception {
        Message message = new Message();
        message.setAuthorId(USER_ID);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post(ADD_MESSAGE, CONVERSATION_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(message)))
                .andReturn();
        Assertions.assertEquals(404, result.getResponse().getStatus());
    }

    @Test
    void getAllConversationsUserNotPresent() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(GET_ALL_CONVERSATIONS, "1"))
                .andReturn();
        Assertions.assertEquals(404, result.getResponse().getStatus());
    }

}
