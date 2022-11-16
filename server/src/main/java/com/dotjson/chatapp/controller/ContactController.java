package com.dotjson.chatapp.controller;

import com.dotjson.chatapp.model.user.UserResponse;
import com.dotjson.chatapp.service.ContactService;
import com.dotjson.chatapp.utils.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dotjson.chatapp.utils.Constant.*;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    ContactService contactService;

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<UserResponse>> getUserContacts(@PathVariable String userId) {
        log.info(START,Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName() );
        try {
            List<UserResponse> response = contactService.getUserContacts(userId);
            log.info(END, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            log.error(ERROR, e, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName() );
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error(ERROR, e, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName() );
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/addContact/{userId}/{contactUsername}")
    public ResponseEntity<UserResponse> addContact(@PathVariable("userId") String userId, @PathVariable("contactUsername") String contactUsername) {
        log.info(START,Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName() );
        try {
            UserResponse response = contactService.addContact(userId, contactUsername);
            log.info(END, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error(ERROR, e, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName() );
            return ResponseEntity.internalServerError().build();
        }

    }
}
