package com.danmoop.apothem.MainApplication.Controller;

import com.danmoop.apothem.MainApplication.DAO.UserDAO;
import com.danmoop.apothem.MainApplication.Model.Post;
import com.danmoop.apothem.MainApplication.Model.User;
import com.danmoop.apothem.MainApplication.Service.PostService;
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
public class BoardController
{
    @Autowired
    private PostService postService;

    @Autowired
    private UserDAO userDAO;

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/getAllPosts")
    public List<Post> getPosts(@RequestBody Object object)
    {
        JSONObject jsonObject = userDAO.getJSON(object);

        return postService.findByTopic(jsonObject.get("topic").toString());
    }

    @PostMapping("/publishAPost")
    public boolean isPostPublished(@RequestBody Object object) throws IOException
    {
        JSONObject jsonObject = userDAO.getJSON(object);

        Post post = objectMapper.readValue(jsonObject.get("post").toString(), Post.class);
        User user = objectMapper.readValue(jsonObject.get("user").toString(), User.class);

        if(userDAO.isUserValid(user))
        {
            postService.save(post);

            return true;
        }

        return false;
    }
}