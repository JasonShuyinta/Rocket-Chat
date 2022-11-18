package com.dotjson.chatapp.controller;

import com.dotjson.chatapp.ChatAppBackendApplicationTests;
import com.dotjson.chatapp.model.user.UserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class UserControllerErrorTest extends ChatAppBackendApplicationTests {

    @Test
    void saveUserWithUsernameNullTest() throws Exception {
        UserRequest input = new UserRequest();
        input.setPassword("Password");
        input.setImage("Image");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post(USER_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andReturn();
        Assertions.assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    void saveUserAlreadyPresentTest() throws Exception {
        UserRequest input = new UserRequest();
        input.setUsername("Test");
        input.setPassword("Password");
        input.setImage("Image");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post(USER_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andReturn();
        Assertions.assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    void getUserByIdNotPresentTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(GET_BY_USER_ID_ENDPOINT, "1"))
                .andReturn();
        Assertions.assertEquals(404, result.getResponse().getStatus());
    }

    @Test
    void loginNotFoundError() throws Exception {
        UserRequest input = new UserRequest();
        input.setUsername("1");
        input.setPassword("Password");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post(USER_LOGIN_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andReturn();
        Assertions.assertEquals(404, result.getResponse().getStatus());
    }

    @Test
    void loginNotMatchingError() throws Exception {
        UserRequest input = new UserRequest();
        input.setUsername("Test");
        input.setPassword("Some password");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post(USER_LOGIN_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andReturn();
        Assertions.assertEquals(400, result.getResponse().getStatus());
    }

}
