package com.danmoop.apothem.MainApplication.DAO;

import com.danmoop.apothem.MainApplication.Model.User;
import com.danmoop.apothem.MainApplication.Interface.UserInterface;
import com.danmoop.apothem.MainApplication.Service.UserService;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDAO implements UserInterface
{
    @Autowired
    private UserService userService;

    @Override
    public User findByUsername(String username)
    {
        return userService.findByUsername(username);
    }

    @Override
    public User findByName(String name)
    {
        return userService.findByName(name);
    }

    @Override
    public User findByToken(String token)
    {
        return userService.findByToken(token);
    }

    @Override
    public List<User> findAll()
    {
        return userService.findAll();
    }

    @Override
    public List<User> getUsersSubscribedTo(String topic)
    {
        List<User> usersSubscribed = new ArrayList<>();
        List<User> allUsers = userService.findAll();

        for (User user : allUsers)
        {
            if (user.getTopics().indexOf(topic) != -1)
            {
                // We can't send actual user object to a client
                user.setPassword(null);
                user.setToken(null);
                usersSubscribed.add(user);
            }
        }

        return usersSubscribed;
    }

    @Override
    public void save(User user)
    {
        userService.save(user);
    }

    public JSONObject getJSON(Object object)
    {
        Gson gson = new Gson();
        String json = gson.toJson(object);

        return new JSONObject(json);
    }
}