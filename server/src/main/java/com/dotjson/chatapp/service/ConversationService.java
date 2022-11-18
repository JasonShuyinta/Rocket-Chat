package com.dotjson.chatapp.service;

import com.dotjson.chatapp.model.conversation.*;
import com.dotjson.chatapp.model.message.Message;
import com.dotjson.chatapp.model.user.User;
import com.dotjson.chatapp.model.user.UserTransformer;
import com.dotjson.chatapp.repository.ConversationRepository;
import com.dotjson.chatapp.repository.MessageRepository;
import com.dotjson.chatapp.repository.UserRepository;
import com.dotjson.chatapp.utils.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.dotjson.chatapp.utils.Constant.*;

@Service
@Slf4j
public class ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private ConversationTransformer conversationTransformer;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserTransformer userTransformer;
    @Autowired
    private MessageRepository messageRepository;

    public ConversationResponse create(ConversationRequest conversationRequest) throws NotFoundException {
        log.info(START, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
        Optional<User> opAdmin = userRepository.findById(conversationRequest.getAdmin().getId());
        if (opAdmin.isEmpty()) {
            log.error(ERROR, USERNAME_NOT_PRESENT, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
            throw new NotFoundException(USERNAME_NOT_PRESENT);
        }
        List<User> recipientsList = new ArrayList<>();
        for (String id : conversationRequest.getRecipients()) {
           Optional<User> opRecipient = userRepository.findById(id);
           if(opRecipient.isEmpty()) throw new NotFoundException("User id " + id + " was not found");
           recipientsList.add(opRecipient.get());
        }
        User admin = opAdmin.get();
        recipientsList.add(opAdmin.get());
        Conversation conversation = new Conversation();
        conversation.setConversationName(conversationRequest.getConversationName());
        conversation.setConversationImage(conversationRequest.getConversationImage());
        conversation.setAdmin(admin);
        conversation.setRecipients(recipientsList);
        conversation.setMessages(new ArrayList<>());
        Conversation savedConversation = conversationRepository.save(conversation);
        //Save conversationId to each recipient
        saveToRecipients(recipientsList, savedConversation.getId());

        log.info(END, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
        return conversationTransformer.modelToDto(savedConversation);
    }



    public ConversationResponse getConversationById(String conversationId) throws NotFoundException {
        log.info(START, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
        Optional<Conversation> opConversation = conversationRepository.findById(conversationId);
        if(opConversation.isEmpty()) {
            log.error(ERROR, CONVERSATION_NOT_PRESENT,  Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
            throw new NotFoundException(CONVERSATION_NOT_PRESENT);
        }
        Conversation conversation = opConversation.get();
        log.info(END, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
        return conversationTransformer.modelToDto(conversation);
    }


    public ConversationResponse addMessage(String conversationId, Message m) throws NotFoundException {
        log.info(START, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
        Optional<Conversation> optionalConversation = conversationRepository.findById(conversationId);
        if(optionalConversation.isEmpty()) {
            log.error(ERROR, CONVERSATION_NOT_PRESENT,  Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
            throw new NotFoundException(CONVERSATION_NOT_PRESENT);
        }
        if(m.getAuthorId() == null ) {
            log.error(ERROR, "Message Author is not present",  Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
            throw new NotFoundException("Message author is not present");
        }
        if(m.getText() == null ) {
            log.error(ERROR, "Message text is not present",  Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
            throw new NotFoundException("Message text is not present");
        }
        m.setTimestamp(LocalDateTime.now());
        Message message = messageRepository.save(m);
        Conversation conversation = optionalConversation.get();
        List<Message> messages = conversation.getMessages();
        messages.add(message);
        Conversation c = conversationRepository.save(conversation);
        log.info(END, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
        return conversationTransformer.modelToDto(c);
    }

    public ConversationFullResponse getAllConversations(String userId) throws NotFoundException {
        log.info(START, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
        Optional<User> opUser = userRepository.findById(userId);
        if(opUser.isEmpty()) {
            log.error(ERROR, USERNAME_NOT_PRESENT,  Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
            throw new NotFoundException(USERNAME_NOT_PRESENT);
        }
        List<ConversationResponse> conversationList = new ArrayList<>();
        for (String conversationId : opUser.get().getConversationId()) {
            Optional<Conversation> opConversation = conversationRepository.findById(conversationId);
            if(opConversation.isEmpty()) {
                log.error(ERROR, CONVERSATION_NOT_PRESENT,  Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
                throw new NotFoundException(CONVERSATION_NOT_PRESENT);
            }
            conversationList.add(conversationTransformer.modelToDto(opConversation.get()));
        }
        log.info(END, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
        ConversationFullResponse response = new ConversationFullResponse();
        response.setConversationList(conversationList);
        return response;
    }

    private void saveToRecipients(List<User> recipientsList, String conversationId)  {
        for (User recipient : recipientsList) {
            List<String> conversationIds = recipient.getConversationId();
            conversationIds.add(conversationId);
            userRepository.save(recipient);
        }
    }
}
