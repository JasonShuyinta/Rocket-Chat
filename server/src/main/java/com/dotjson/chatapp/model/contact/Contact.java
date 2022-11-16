package com.dotjson.chatapp.model.contact;

import com.dotjson.chatapp.model.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@Document
public class Contact {

    @Id
    private String id;
    private String mainUserId;
    private List<User> contacts;
}
