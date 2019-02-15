package com.danmoop.apothem.MainApplication.Controller;

import com.danmoop.apothem.MainApplication.DAO.UserDAO;
import com.danmoop.apothem.MainApplication.Model.Post;
import com.danmoop.apothem.MainApplication.Service.PostService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:8100")
public class BoardController
{
    @Autowired
    private PostService postService;

    @Autowired
    private UserDAO userDAO;

    @PostMapping("/createSomePosts")
    public void createSomePosts(@RequestBody Post post)
    {
        postService.save(post);
    }

    @PostMapping("/getAllPosts")
    public List<Post> getPosts(@RequestBody Object object)
    {
        JSONObject jsonObject = userDAO.getJSON(object);

        return postService.findByTopic(jsonObject.get("topic").toString());
    }
}