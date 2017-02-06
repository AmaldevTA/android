package com.amal.eventbus;

/**
 * Created by amal on 31/5/16.
 */
public class AnotherEvent {
    private final String message;

    public AnotherEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
