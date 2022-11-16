package com.dotjson.chatapp.controller;

import com.dotjson.chatapp.model.user.UserRequest;
import com.dotjson.chatapp.model.user.UserResponse;
import com.dotjson.chatapp.service.UserService;
import com.dotjson.chatapp.utils.BadRequestException;
import com.dotjson.chatapp.utils.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.dotjson.chatapp.utils.Constant.*;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String userId) {
        log.info(START,Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName() );
        try {
            UserResponse response = userService.getUserById(userId);
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

    @PostMapping
    public ResponseEntity<UserResponse> saveUser(@RequestBody UserRequest userRequest) {
        log.info(START,Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName() );
        try {
            UserResponse response = userService.save(userRequest);
            log.info(END, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
            return ResponseEntity.ok(response);
        } catch (BadRequestException e) {
            log.error(ERROR, e, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error(ERROR, e, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName() );
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserRequest userRequest) {
        log.info(START,Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName() );
        try {
            UserResponse response = userService.login(userRequest);
            log.info(END, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            log.error(ERROR, e, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
            return ResponseEntity.notFound().build();
        } catch (BadRequestException e) {
            log.error(ERROR, e, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error(ERROR, e, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName() );
            return ResponseEntity.internalServerError().build();
        }
    }

}
