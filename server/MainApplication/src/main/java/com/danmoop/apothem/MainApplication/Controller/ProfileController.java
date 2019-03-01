package com.danmoop.apothem.MainApplication.Controller;

import com.danmoop.apothem.MainApplication.DAO.UserDAO;
import com.danmoop.apothem.MainApplication.Model.Dialog;
import com.danmoop.apothem.MainApplication.Model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
public class ProfileController
{
    @Autowired
    private UserDAO userDAO;

    private ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/applyNewTopics")
    public void applyNewTopics(@RequestBody User user)
    {
        User userDB = userDAO.findByUsername(user.getUsername());

        if (userDAO.isUserValid(user))
        {
            List<String> topics = user.getTopics(); // list of topics user has chosen by setting checkboxes

            userDB.setTopics(topics); // apply new topics to user

            userDAO.save(userDB);
        }
    }

    @PostMapping("/subscribeToTopic")
    public void subscribeToTopic(@RequestBody Object object) throws IOException
    {
        JSONObject jsonObject = userDAO.getJSON(object);

        User user = mapper.readValue(jsonObject.get("user").toString(), User.class);
        String topic = jsonObject.get("item").toString();

        if(userDAO.isUserValid(user))
        {
            User userDB = userDAO.findByUsername(user.getUsername());

            userDB.addTopic(topic);

            userDAO.save(userDB);
        }
    }

    @PostMapping("/unsubscribeFromTopic")
    public void unsubscribeFromTopic(@RequestBody Object object) throws IOException
    {
        JSONObject jsonObject = userDAO.getJSON(object);
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(jsonObject.get("user").toString(), User.class);
        String topic = jsonObject.get("item").toString();

        if(userDAO.isUserValid(user))
        {
            User userDB = userDAO.findByUsername(user.getUsername());

            userDB.removeTopic(topic);

            userDAO.save(userDB);
        }
    }

    @PostMapping("/getUserProfile")
    public User getUserProfile(@RequestBody Object object)
    {
        String username = userDAO.getJSON(object).get("username").toString();

        User userDB = userDAO.findByUsername(username);

        if(userDB != null)
        {
            //We can't send private information to another user
            userDB.hideData();

            return userDB;
        }

        return null;
    }
}