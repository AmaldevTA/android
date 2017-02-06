package com.amal.eventbus;

/**
 * Created by amaldev on 30/5/16.
 */
public class HelloWorldEvent {
    private final String message;

    public HelloWorldEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}