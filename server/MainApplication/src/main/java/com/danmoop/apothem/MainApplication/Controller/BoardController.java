package com.danmoop.apothem.MainApplication.Controller;

import com.danmoop.apothem.MainApplication.DAO.UserDAO;
import com.danmoop.apothem.MainApplication.Model.Post;
import com.danmoop.apothem.MainApplication.Model.User;
import com.danmoop.apothem.MainApplication.Service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
            post.generateTime();
            post.generateKey();
            post.setComments(new ArrayList<>());
            postService.save(post);

            return true;
        }

        return false;
    }

    @PostMapping("/deletePost")
    public void deletePost(@RequestBody Object object) throws IOException
    {
        JSONObject obj = userDAO.getJSON(object);

        Post post = objectMapper.readValue(obj.get("post").toString(), Post.class);

        Post postDB = postService.findByKey(post.getKey());

        User user = objectMapper.readValue(obj.get("user").toString(), User.class);

        if(postDB != null && userDAO.isUserValid(user) && user.getUsername().equals(post.getAuthor()))
        {
            postService.delete(postDB);
        }
    }

    @GetMapping("/getLocalDate")
    public String now()
    {
        return new Date().toLocaleString();
    }

    @PostMapping("/refreshPost")
    public Post returnRefreshedPost(@RequestBody Post post)
    {
        Post postDB = postService.findByKey(post.getKey());

        if(postDB != null)
            return postDB;

        return null;
    }
}