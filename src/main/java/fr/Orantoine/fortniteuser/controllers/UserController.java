package fr.Orantoine.fortniteuser.controllers;


import fr.Orantoine.fortniteuser.models.User;
import fr.Orantoine.fortniteuser.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/health")
    public String HealthCheck(){
        return "OK";
    }


    @GetMapping(value = "/users")
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    @PostMapping(value = "/user")
    public User addUser(@RequestBody User user){
        userRepository.save(user);
        return user;
    }

    @DeleteMapping(value = "/user/{id}")
    public String deleteUser(@PathVariable String id){
        userRepository.deleteById(id);
        return "Deleted";
    }

}
