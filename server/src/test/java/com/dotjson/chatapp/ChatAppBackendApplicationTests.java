package com.dotjson.chatapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Random;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration(classes = {CoreTestConfiguration.class})
public abstract class ChatAppBackendApplicationTests {

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	protected WebApplicationContext wac;

	public MockMvc mockMvc;

	//user
	public static final String USER_ENDPOINT = "/user";
	public static final String GET_BY_USER_ID_ENDPOINT = USER_ENDPOINT+"/{userId}";
	public static final String USER_LOGIN_ENDPOINT = USER_ENDPOINT+"/login";

	//conversation
	public static final String CONVERSATION_ENDPOINT = "/conversation";
	public static final String CREATE_CONVERSATION_ENDPOINT = CONVERSATION_ENDPOINT+"/create";
	public static final String GET_CONVERSATION_BY_ID_ENDPOINT = CONVERSATION_ENDPOINT+"/{conversationId}";
	public static final String ADD_MESSAGE = CONVERSATION_ENDPOINT+"/addMessage/{conversationId}";
	public static final String GET_ALL_CONVERSATIONS = CONVERSATION_ENDPOINT+"/getAllConversations/{userId}";

	//contacts
	public static final String GET_ALL_CONTACTS_ENDPOINT = "/contact/all/{userId}";
	public static final String ADD_CONTACT_ENDPOINT = "/contact/addContact/{userId}/{contactUsername}";

	//ids
	public static final String CONVERSATION_ID = "637791732067072391589039";
	public static final String USER_ID = "63778a79bfe02418a37884d6";

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	public String getRandomString() {
		String asciiUpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String asciiLowerCase = asciiUpperCase.toLowerCase();
		String digits = "1234567890";
		String asciiChars = asciiUpperCase + asciiLowerCase + digits;
		return generateRandomString(asciiChars);
	}

	private static String generateRandomString(String seedChars) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		Random rand = new Random();
		while (i < 12) {
			sb.append(seedChars.charAt(rand.nextInt(seedChars.length())));
			i++;
		}
		return sb.toString();
	}
}
