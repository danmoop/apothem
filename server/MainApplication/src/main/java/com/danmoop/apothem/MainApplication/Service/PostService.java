package com.danmoop.apothem.MainApplication.Service;

import com.danmoop.apothem.MainApplication.Model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostService extends MongoRepository<Post, String>
{
    List<Post> findAll();
    List<Post> findByTopic(String topic);
    List<Post> findByAuthor(String author);
    Post findByKey(String key);
}