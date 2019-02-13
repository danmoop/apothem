package com.danmoop.apothem.MainApplication.Controller;

import com.danmoop.apothem.MainApplication.Model.User;
import com.danmoop.apothem.MainApplication.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:8100")
public class ProfileController
{
    @Autowired
    private UserService userService;

    @PostMapping("/applyNewTopics")
    public void applyNewTopics(@RequestBody User user)
    {
        User userDB = userService.findByUsername(user.getUsername());

        if (isUserValid(user) && userDB != null)
        {
            List<String> topics = user.getTopics();

            userDB.setTopics(topics);

            userService.save(userDB);
        }
    }

    @PostMapping("/unsubscribeFromTopic")
    public void unsubscribeFromTopic(@RequestBody Object object) throws IOException
    {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        JSONObject jsonObject = new JSONObject(json);
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(jsonObject.get("user").toString(), User.class);
        String topic = jsonObject.get("item").toString();

        System.out.println(user.toString());
        System.out.println(topic);

        if(isUserValid(user))
        {
            User userDB = userService.findByUsername(user.getUsername());

            userDB.removeTopic(topic);

            userService.save(userDB);
        }
    }

    private boolean isUserValid(User user)
    {
        User userDB = userService.findByUsername(user.getUsername());

        return userDB != null && user.getToken().equals(userDB.getToken()) && userDB.getName().equals(user.getName());
    }
}