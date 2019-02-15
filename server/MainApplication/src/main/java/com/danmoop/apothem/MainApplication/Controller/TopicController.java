package com.danmoop.apothem.MainApplication.Controller;

import com.danmoop.apothem.MainApplication.Model.User;
import com.danmoop.apothem.MainApplication.DAO.UserDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:8100")
public class TopicController
{
    @Autowired
    private UserDAO userDAO;

    private ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/isUserSubscribed")
    private boolean isUserSubscribedOn(@RequestBody Object object) throws IOException
    {
        JSONObject jsonObject = userDAO.getJSON(object);

        User user = mapper.readValue(jsonObject.get("user").toString(), User.class);

        User userDB = userDAO.findByUsername(user.getUsername());

        String topic = jsonObject.get("topic").toString();

        if(isUserValid(userDB))
            return userDB.getTopics().indexOf(topic) != -1;

        return false;
    }

    private boolean isUserValid(User user)
    {
        User userDB = userDAO.findByUsername(user.getUsername());

        return userDB != null && user.getToken().equals(userDB.getToken()) && userDB.getName().equals(user.getName());
    }

    @PostMapping("/getUsersSubscribedOnTopic")
    private List<User> getUsersSubscribedOn(@RequestBody Object topicObject)
    {
        JSONObject obj = userDAO.getJSON(topicObject);

        return userDAO.getUsersSubscribedTo(obj.get("topic").toString());
    }
}