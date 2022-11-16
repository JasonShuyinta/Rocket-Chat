package com.dotjson.chatapp.model.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@NoArgsConstructor
public class User {

    @Id
    private String id;
    private String username;
    private String password;
    private String image;
    private List<String> conversationId;

}
