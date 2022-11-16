package com.dotjson.chatapp.model.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ContactRequest {

    @JsonProperty("id")
    private String id;
    @JsonProperty("contactUsername")
    private String contactUsername;
    @JsonProperty("contactImage")
    private String contactImage;
}
