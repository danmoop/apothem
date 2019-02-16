package com.danmoop.apothem.MainApplication.Controller;

import com.danmoop.apothem.MainApplication.DAO.UserDAO;
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

        ObjectMapper mapper = new ObjectMapper();
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
}