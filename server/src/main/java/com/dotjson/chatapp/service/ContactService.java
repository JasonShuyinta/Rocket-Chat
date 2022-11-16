package com.dotjson.chatapp.service;

import com.dotjson.chatapp.model.contact.Contact;
import com.dotjson.chatapp.model.contact.ContactResponse;
import com.dotjson.chatapp.model.user.User;
import com.dotjson.chatapp.model.user.UserRequest;
import com.dotjson.chatapp.model.user.UserResponse;
import com.dotjson.chatapp.model.user.UserTransformer;
import com.dotjson.chatapp.repository.ContactRepository;
import com.dotjson.chatapp.repository.UserRepository;
import com.dotjson.chatapp.utils.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.dotjson.chatapp.utils.Constant.*;

@Service
@Slf4j
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserTransformer userTransformer;

    public List<UserResponse> getUserContacts(String userId) throws NotFoundException {
        log.info(START, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
        Optional<User> op = userRepository.findById(userId);
        if (op.isEmpty()) return new ArrayList<>();
        Optional<Contact> opContact = contactRepository.findByMainUserId(userId);
        if(opContact.isEmpty()) {
            log.error(ERROR, CONTACT_NOT_PRESENT, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
            throw new NotFoundException(CONTACT_NOT_PRESENT);
        }
        List<UserResponse> response = new ArrayList<>();
        Contact contacts = opContact.get();
        if (contacts.getContacts().isEmpty()) return response;
        for (User user : contacts.getContacts()) {
            response.add(userTransformer.modelToDto(user));
        }
        log.info(END, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
        return response;
    }


    public UserResponse addContact(String userId, String contactUsername) throws NotFoundException {
        log.info(START, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
        Optional<User> opUser = userRepository.findByUsername(contactUsername);
        if(opUser.isEmpty()) {
            log.error(ERROR,USERNAME_NOT_PRESENT, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
            throw new NotFoundException(CONTACT_NOT_PRESENT);
        }
        User contact = opUser.get();
        Optional<Contact> op = contactRepository.findByMainUserId(userId);
        if(op.isEmpty()) {
            log.error(ERROR,CONTACT_NOT_PRESENT, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
            throw new NotFoundException(CONTACT_NOT_PRESENT);
        }
        Contact c = op.get();
        List<User> contactList = c.getContacts();
        contactList.add(contact);
        contactRepository.save(c);
        log.info(END, Thread.currentThread().getStackTrace()[1].getMethodName(), this.getClass().getSimpleName());
        return userTransformer.modelToDto(contact);
    }
}
