package com.dotjson.chatapp.utils;

import lombok.EqualsAndHashCode;
import lombok.Generated;

@Generated
@EqualsAndHashCode(callSuper = true)
public class UsernameAlreadyTakenException extends Exception {

    public UsernameAlreadyTakenException() {
        super();
    }

    public UsernameAlreadyTakenException(String msg) {
        super(msg);
    }
}
