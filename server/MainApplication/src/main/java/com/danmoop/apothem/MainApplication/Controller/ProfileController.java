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
import java.util.ArrayList;
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

        if (userDAO.isUserValid(user) && userDB != null)
        {
            List<String> topics = user.getTopics();

            userDB.setTopics(topics);

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
            //We can't send that information to another user
            userDB.hideData();

            return userDB;
        }

        return null;
    }

    @PostMapping("/createNewDialog")
    public void createNewDialog(@RequestBody Object object) throws IOException
    {
        JSONObject json = userDAO.getJSON(object);

        String recepient = json.get("recepient").toString();
        User user = mapper.readValue(json.get("user").toString(), User.class);

        if(userDAO.isUserValid(user) && !user.isDialogExists(recepient))
        {
            User userDB = userDAO.findByUsername(user.getUsername());

            userDB.addDialog(recepient);

            userDAO.save(userDB);
        }
    }

    @PostMapping("/removeDialog")
    public User userAfterRemovingDialog(@RequestBody Object object) throws IOException {
        JSONObject json = userDAO.getJSON(object);

        User user = mapper.readValue(json.get("user").toString(), User.class);
        Dialog dialog = mapper.readValue(json.get("dialog").toString(), Dialog.class);

        if(userDAO.isUserValid(user))
        {
            User userDB = userDAO.findByUsername(user.getUsername());

            userDB.removeDialog(dialog);
            userDAO.save(userDB);

            return userDB;
        }

        return null;
    }
}