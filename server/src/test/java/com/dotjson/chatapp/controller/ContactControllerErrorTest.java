package com.dotjson.chatapp.controller;

import com.dotjson.chatapp.ChatAppBackendApplicationTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class ContactControllerErrorTest extends ChatAppBackendApplicationTests {

    @Test
    void addContactcUserNotPresentError() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(ADD_CONTACT_ENDPOINT,"1", "1"))
                .andReturn();
        Assertions.assertEquals(404, result.getResponse().getStatus());
    }

    @Test
    void addContactcContactNotPresentError() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(ADD_CONTACT_ENDPOINT,USER_ID, "1"))
                .andReturn();
        Assertions.assertEquals(404, result.getResponse().getStatus());
    }
}
