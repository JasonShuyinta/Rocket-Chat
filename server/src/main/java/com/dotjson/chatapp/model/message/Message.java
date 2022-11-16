package com.dotjson.chatapp.model.message;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
@NoArgsConstructor
public class Message {

    @Id
    private String id;
    private String authorId;
    private String text;
    private LocalDateTime timestamp;
}
