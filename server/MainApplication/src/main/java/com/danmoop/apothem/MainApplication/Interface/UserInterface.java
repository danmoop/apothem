package com.danmoop.apothem.MainApplication.Interface;

import com.danmoop.apothem.MainApplication.Model.User;

import java.util.List;

public interface UserInterface
{
    User findByUsername(String username);
    User findByName(String name);
    User findByToken(String token);
    List<User> findAll();
    List<User> getUsersSubscribedTo(String topic);
    void save(User user);
}