package com.danmoop.apothem.MainApplication.Model;

import java.util.List;
import java.util.UUID;

public class Dialog
{
    private String recepient;
    private List<Message> messages;
    private String key;

    public Dialog(){}

    public Dialog(String recepient, List<Message> messages)
    {
        this.recepient = recepient;
        this.messages = messages;
        this.key = UUID.randomUUID().toString();
    }

    public String getRecepient()
    {
        return recepient;
    }

    public List<Message> getMessages()
    {
        return messages;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "{recepient: " + recepient + "}, {messages: " + messages + "}, {key: " + key + "}";
    }
}