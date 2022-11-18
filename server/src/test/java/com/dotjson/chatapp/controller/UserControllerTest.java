package com.dotjson.chatapp.controller;

import com.dotjson.chatapp.ChatAppBackendApplicationTests;
import com.dotjson.chatapp.model.user.UserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class UserControllerTest extends ChatAppBackendApplicationTests {

    @Test
    void saveUserTest() throws Exception {
        UserRequest input = new UserRequest();
        input.setUsername(getRandomString());
        input.setPassword("Password");
        input.setImage("Image");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post(USER_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void getUserById() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(GET_BY_USER_ID_ENDPOINT, USER_ID))
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void login() throws Exception {
        UserRequest input = new UserRequest();
        input.setUsername("Test");
        input.setPassword("Password");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post(USER_LOGIN_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());

    }
}
