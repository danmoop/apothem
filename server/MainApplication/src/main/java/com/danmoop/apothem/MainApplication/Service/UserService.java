package com.danmoop.apothem.MainApplication.Service;

import com.danmoop.apothem.MainApplication.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserService extends MongoRepository<User, String>
{
    User findByUsername(String username);
    User findByToken(String token);

    List<User> findAll();
}