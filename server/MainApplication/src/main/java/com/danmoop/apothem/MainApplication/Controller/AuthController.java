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
    /*
        Well, I actually wanted to implement Spring Security auth, but couldn't

        So here is the auth system that simply checks user's instance
        stored on a mobile phone with a user's instance on the server
     */

    @Autowired
    private UserDAO userDAO;

    @PostMapping("/register")
    public String registerResponse(@RequestBody User user)
    {
        User userDB = userDAO.findByUsername(user.getUsername());

        if(userDB != null)
            return "This username is already taken";

        /*
            By default there is only user's username and password are present
            so generateData method initializes all the arrays (like user's messages, topics etc)
         */
        user.generateData();

        userDAO.save(user);

        // We return string result in order for a user to understand if registration is successful
        return "Success";
    }

    @PostMapping("/login")
    public User userLogin(@RequestBody User user)
    {
        User userDB = userDAO.findByUsername(user.getUsername());

        if(userDB != null && user.getPassword().equals(userDB.getPassword()))
        {
            // Every time user is logged in their token is changed
            userDB.setToken(UUID.randomUUID().toString());

            userDAO.save(userDB);

            userDB.setPassword(""); // no need to send a password, kind of unsafe
            return userDB;
        }

        return null;
    }


    /* We send a request to this method every time we open the app and home screen in app.
       So again, if password, username and token are match, everything is fine

       But if something is changed on the client's side,
       data won't match and you will have to log in again (that's the best system I could implement).
       But it's not the right solution
    */
    @PostMapping("/getUser")
    public User getUser(@RequestBody User user)
    {
        User userDB = userDAO.findByUsername(user.getUsername());

        if(isUserValid(user) && userDB != null)
            return userDB;

        return null;
    }

    /* That's the method that is used in the previous method
    It just checks if all the data are the same as on the server */
    private boolean isUserValid(User user)
    {
        User userDB = userDAO.findByUsername(user.getUsername());

        return userDB != null && user.getToken().equals(userDB.getToken()) && userDB.getName().equals(user.getName());
    }
}