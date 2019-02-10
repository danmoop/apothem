package com.danmoop.apothem.MainApplication.Controller;

import com.danmoop.apothem.MainApplication.Model.User;
import com.danmoop.apothem.MainApplication.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@CrossOrigin(value = "http://localhost:8100")
public class AuthController
{
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public String registerResponse(@RequestBody User user)
    {
        User userDB = userService.findByUsername(user.getUsername());

        if(userDB != null)
            return "This username is already taken";

        user.setToken(UUID.randomUUID().toString());

        userService.save(user);

        return "Success";
    }
}