package com.danmoop.apothem.MainApplication.Controller;

import com.danmoop.apothem.MainApplication.DAO.UserDAO;
import com.danmoop.apothem.MainApplication.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
public class AuthController
{
    @Autowired
    private UserDAO userDAO;

    @PostMapping("/register")
    public String registerResponse(@RequestBody User user)
    {
        User userDB = userDAO.findByUsername(user.getUsername());

        if(userDB != null)
            return "This username is already taken";

        user.generateData();

        userDAO.save(user);

        return "Success";
    }

    @PostMapping("/login")
    public User userLogin(@RequestBody User user)
    {
        User userDB = userDAO.findByUsername(user.getUsername());

        if(userDB != null && user.getPassword().equals(userDB.getPassword()))
        {
            userDB.setToken(UUID.randomUUID().toString());

            userDAO.save(userDB);

            userDB.setPassword(""); // no need
            return userDB;
        }

        return null;
    }

    @PostMapping("/getUser")
    public User getUser(@RequestBody User user)
    {
        User userDB = userDAO.findByUsername(user.getUsername());

        if(isUserValid(user) && userDB != null)
            return userDB;

        return null;
    }

    private boolean isUserValid(User user)
    {
        User userDB = userDAO.findByUsername(user.getUsername());

        return userDB != null && user.getToken().equals(userDB.getToken()) && userDB.getName().equals(user.getName());
    }
}