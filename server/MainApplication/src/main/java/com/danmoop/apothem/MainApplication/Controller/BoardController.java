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


    // This method returns a list of topics depending on what topic user send in their request
    @PostMapping("/getAllPosts")
    public List<Post> getPosts(@RequestBody Object object)
    {
        JSONObject jsonObject = userDAO.getJSON(object);

        return postService.findByTopic(jsonObject.get("topic").toString());
    }

    /*  Well, we send user object and post object, checks if user is valid
        and then generate post data (besides post's title and content, that
        are typed by user, but like timestamp and other)
    */
    @PostMapping("/publishAPost")
    public boolean isPostPublished(@RequestBody Object object) throws IOException
    {
        JSONObject jsonObject = userDAO.getJSON(object);

        Post post = objectMapper.readValue(jsonObject.get("post").toString(), Post.class);
        User user = objectMapper.readValue(jsonObject.get("user").toString(), User.class);

        if(userDAO.isUserValid(user))
        {
            post.generateData();
            postService.save(post);

            return true;
        }

        return false;
    }

    /*
        This method finds post by key and deletes it if authors are match
        (So it checks that some user couldn't delete another user's post)
     */
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

    /*
        When something is added to the post, user sends a request to get the new post instance
        (e.g new comments appear, timestamp changes etc)
     */
    @PostMapping("/refreshPost")
    public Post returnRefreshedPost(@RequestBody Post post)
    {
        Post postDB = postService.findByKey(post.getKey());

        if(postDB != null)
            return postDB;

        return null;
    }
}