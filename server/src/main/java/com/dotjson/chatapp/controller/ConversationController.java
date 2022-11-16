package com.dotjson.chatapp.controller;

import com.dotjson.chatapp.model.conversation.Conversation;
import com.dotjson.chatapp.model.conversation.ConversationFullResponse;
import com.dotjson.chatapp.model.conversation.ConversationRequest;
import com.dotjson.chatapp.model.conversation.ConversationResponse;
import com.dotjson.chatapp.model.message.Message;
import com.dotjson.chatapp.service.ConversationService;
import com.dotjson.chatapp.utils.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.dotjson.chatapp.utils.Constant.*;

@RestController
@RequestMapping("/conversation")
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @PostMapping("/create")
    public ResponseEntity<ConversationResponse> createConversation(@RequestBody ConversationRequest conversationRequest) {
        log.info(START, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
        try {
            ConversationResponse response = conversationService.create(conversationRequest);
            log.info(END_CONTROLLER, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName(), response.toString());
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            log.error(ERROR, e, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error(ERROR, e, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{conversationId}")
    public ResponseEntity<ConversationResponse> getConversationById(@PathVariable String conversationId) {
        log.info(START, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
        try {
            ConversationResponse response = conversationService.getConversationById(conversationId);
            log.info(END_CONTROLLER, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName(), response.toString());
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            log.error(ERROR, e, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error(ERROR, e, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/addMessage/{conversationId}")
    public ResponseEntity<ConversationResponse> addMessagge(@PathVariable String conversationId, @RequestBody Message message) {
        log.info(START, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
        try {
            ConversationResponse response = conversationService.addMessage(conversationId, message);
            log.info(END_CONTROLLER, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName(), response.toString());
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            log.error(ERROR, e, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error(ERROR, e, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/getAllConversations/{userId}")
    public ResponseEntity<ConversationFullResponse> getAllConversations(@PathVariable String userId) {
        log.info(START, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
        try {
            ConversationFullResponse response = conversationService.getAllConversations(userId);
            log.info(END_CONTROLLER, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName(), response.toString());
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            log.error(ERROR, e, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error(ERROR, e, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
            return ResponseEntity.internalServerError().build();
        }

    }

}
