package com.danmoop.apothem.MainApplication.Controller;

import com.danmoop.apothem.MainApplication.Model.User;
import com.danmoop.apothem.MainApplication.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
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
    private UserService userService;

    @PostMapping("/isUserSubscribed")
    private boolean isUserSubscribedOn(@RequestBody Object object) throws IOException
    {
        JSONObject jsonObject = getJSON(object);

        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(jsonObject.get("user").toString(), User.class);

        User userDB = userService.findByUsername(user.getUsername());

        String topic = jsonObject.get("topic").toString();

        if(isUserValid(userDB))
            return userDB.getTopics().indexOf(topic) != -1;

        return false;
    }

    private boolean isUserValid(User user)
    {
        User userDB = userService.findByUsername(user.getUsername());

        return userDB != null && user.getToken().equals(userDB.getToken()) && userDB.getName().equals(user.getName());
    }

    private JSONObject getJSON(Object object)
    {
        Gson gson = new Gson();
        String json = gson.toJson(object);

        return new JSONObject(json);
    }

    @PostMapping("/getUsersSubscribedOnTopic")
    private List<User> getUsersSubscribedOn(@RequestBody Object topicObject)
    {
        List<User> usersSubscribed = new ArrayList<>();
        List<User> allUsers = userService.findAll();

        String topic = getJSON(topicObject).get("topic").toString();

        for (int i = 0; i < allUsers.size(); i++)
        {
            User user = allUsers.get(i);

            if(user.getTopics().indexOf(topic) != -1)
            {
                // We can't send actual user object to a client
                user.setPassword(null);
                user.setToken(null);

                usersSubscribed.add(user);
            }
        }

        return usersSubscribed;
    }
}