package com.dotjson.chatapp.service;

import com.dotjson.chatapp.model.contact.Contact;
import com.dotjson.chatapp.model.user.User;
import com.dotjson.chatapp.model.user.UserRequest;
import com.dotjson.chatapp.model.user.UserResponse;
import com.dotjson.chatapp.model.user.UserTransformer;
import com.dotjson.chatapp.repository.ContactRepository;
import com.dotjson.chatapp.repository.UserRepository;
import com.dotjson.chatapp.utils.BadRequestException;
import com.dotjson.chatapp.utils.IncorrectCredentialsException;
import com.dotjson.chatapp.utils.NotFoundException;
import com.dotjson.chatapp.utils.UsernameAlreadyTakenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

import static com.dotjson.chatapp.utils.Constant.*;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private UserTransformer userTransformer;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponse getUserById(String id) throws NotFoundException {
        log.info(START, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
        Optional<User> op = userRepository.findById(id);
        if (op.isEmpty()) throw new NotFoundException("User with id " + id + " not found");
        log.info(END, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
        return userTransformer.modelToDto(op.get());
    }

    public UserResponse save(UserRequest userRequest) throws BadRequestException, UsernameAlreadyTakenException {
        log.info(START, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
        if (userRequest.getUsername() == null || userRequest.getPassword() == null) {
            log.error(ERROR, USERNAME_PASSWORD_MISSING,Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName() );
            throw new BadRequestException(USERNAME_PASSWORD_MISSING);
        }
        Optional<User> userExists = userRepository.findByUsername(userRequest.getUsername());
        if (userExists.isPresent()) {
            log.error(ERROR, "Username is already taken",Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName() );
            throw new UsernameAlreadyTakenException("Username is already taken");
        }
        else {
            User userModel = userTransformer.dtoToModel(userRequest);
            userModel.setConversationId(new ArrayList<>());
            userModel.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            UserResponse response = userTransformer.modelToDto(userRepository.save(userModel));
            Contact contactModel = new Contact();
            contactModel.setMainUserId(response.getId());
            contactModel.setContacts(new ArrayList<>());
            contactRepository.save(contactModel);
            log.info(END, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
            return response;
        }
    }

    public UserResponse login(UserRequest userRequest) throws BadRequestException, NotFoundException, IncorrectCredentialsException {
        log.info(START, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
        if(userRequest.getUsername() == null || userRequest.getPassword() == null) {
            log.error(ERROR, USERNAME_PASSWORD_MISSING,Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName() );
            throw new BadRequestException(USERNAME_PASSWORD_MISSING);
        }
        Optional<User> userExists = userRepository.findByUsername(userRequest.getUsername());
        if(userExists.isEmpty()) {
            log.error(ERROR,USERNAME_NOT_PRESENT,Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName() );
            throw new NotFoundException(USERNAME_NOT_PRESENT);
        }
        User user = userExists.get();
        if(!passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
            log.error(ERROR, "Incorrect credentials",Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName() );
            throw new IncorrectCredentialsException("Incorrect credentials");
        }
        log.info(END, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
        return userTransformer.modelToDto(user);
    }
}
