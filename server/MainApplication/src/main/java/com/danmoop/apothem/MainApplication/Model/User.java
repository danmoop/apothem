package com.danmoop.apothem.MainApplication.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document(value = "users")
public class User
{
    @Id
    private String id;

    private String username;
    private String name;
    private String password;
    private String token;
    private List<Dialog> dialogs;

    public List<Dialog> getDialogs() {
        return dialogs;
    }

    public void setDialogs(List<Dialog> dialogs) {
        this.dialogs = dialogs;
    }

    private List<String> topics;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void addTopic(String topic)
    {
        topics.add(topic);
    }

    public void removeTopic(String topic)
    {
        topics.remove(topic);
    }

    public void addDialog(String recepient)
    {
        dialogs.add(new Dialog(recepient, new ArrayList<>()));
    }

    public boolean isDialogExists(String recepient)
    {
        for (Dialog dialog: dialogs)
        {
            if(dialog.getRecepient().equals(recepient))
                return true;
        }

        return false;
    }

    //When user is registered, only username & password fields are filled, token and arrays should be generate after
    public void generateData()
    {
        setToken(UUID.randomUUID().toString());
        setTopics(new ArrayList<>());
        setDialogs(new ArrayList<>());
    }

    //This method is used when user instance sent to another user.
    // Nobody else shouldn't know user's password & private stuff
    public void hideData()
    {
        setToken(null);
        setPassword(null);
        setDialogs(null);
    }

    public void removeDialog(Dialog dialog)
    {
        // dialogs.remove(dialog) doesn't work for some reason
        for (int i = 0; i < dialogs.size(); i++)
        {
            if(dialog.getRecepient().equals(dialogs.get(i).getRecepient()) && dialog.getKey().equals(dialogs.get(i).getKey()))
            {
                dialogs.remove(dialogs.get(i));
            }
        }
    }
}