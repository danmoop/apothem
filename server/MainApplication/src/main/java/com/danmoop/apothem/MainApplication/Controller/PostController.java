package com.danmoop.apothem.MainApplication.Controller;

import com.danmoop.apothem.MainApplication.DAO.UserDAO;
import com.danmoop.apothem.MainApplication.Model.Comment;
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

@RestController
@CrossOrigin
public class PostController
{
    @Autowired
    private PostService postService;

    @Autowired
    private UserDAO userDAO;

    private ObjectMapper mapper = new ObjectMapper();

    /*
        It organizes all the data sent by user and if everything is valid - publishes a post
     */
    @PostMapping("/publishAComment")
    public Post publishAComment(@RequestBody Object object) throws IOException
    {
        JSONObject obj = userDAO.getJSON(object);

        User user = mapper.readValue(obj.get("user").toString(), User.class);
        Comment comment = mapper.readValue(obj.get("comment").toString(), Comment.class);
        Post postDB = postService.findByKey(mapper.readValue(obj.get("post").toString(), Post.class).getKey());

        if(userDAO.isUserValid(user))
        {
            comment.generateTime();
            postDB.addComment(comment);
            postService.save(postDB);
        }

        return postDB;
    }
}