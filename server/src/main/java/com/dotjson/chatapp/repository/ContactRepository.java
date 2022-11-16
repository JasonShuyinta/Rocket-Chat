package com.dotjson.chatapp.repository;

import com.dotjson.chatapp.model.contact.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ContactRepository extends MongoRepository<Contact, String> {

    Optional<Contact> findByMainUserId(String userId);
}
