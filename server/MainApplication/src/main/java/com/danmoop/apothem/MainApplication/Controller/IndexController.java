package com.danmoop.apothem.MainApplication.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@CrossOrigin
public class IndexController
{
    @GetMapping("/hello")
    public String hello()
    {
        return "Hello there!";
    }

    @GetMapping("/getId")
    public String getRandomId()
    {
        return UUID.randomUUID().toString();
    }
}