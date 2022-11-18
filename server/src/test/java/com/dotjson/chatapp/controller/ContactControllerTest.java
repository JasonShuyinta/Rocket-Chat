package com.dotjson.chatapp.controller;

import com.dotjson.chatapp.ChatAppBackendApplicationTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class ContactControllerTest extends ChatAppBackendApplicationTests {

    @Test
    void getUserContacts() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(GET_ALL_CONTACTS_ENDPOINT, USER_ID))
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void addContact() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(ADD_CONTACT_ENDPOINT, USER_ID,"YPNiwGHe6n6I"))
                .andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }
}
