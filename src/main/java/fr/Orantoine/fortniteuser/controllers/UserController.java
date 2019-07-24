package fr.Orantoine.fortniteuser.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import fr.Orantoine.fortniteuser.classes.MailHelper;
import fr.Orantoine.fortniteuser.models.Response;
import fr.Orantoine.fortniteuser.models.User;
import fr.Orantoine.fortniteuser.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
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
    @GetMapping(value = "/user/forget")
    public User forgetPassword(@RequestParam String email){
        User user = userRepository.findByEmail(email);
        MailHelper mailHelper = new MailHelper();
        mailHelper.sendEmail(user.getEmail());
        return user;
    }

    @GetMapping(value = "/login")
    public String login(@RequestParam(required =true) String pseudo, @RequestParam(required = true) String password){
        Response response = new Response();
        User user = userRepository.findByPseudo(pseudo);
        if(user == null ){
            user = userRepository.findByEmail(pseudo);
        }
        if(user == null) {
            if(user.getPassword().equals(password)){
                response.setCode(200);
                response.setMessage("Vous êtes correctement idéntifié");
            }
        }
        else{
            response.setCode(500);
            response.setMessage("Identifiant inconnus");
        }

        ObjectMapper obj = new ObjectMapper();
        try{
            String jsonStr = obj.writeValueAsString(response);
            return jsonStr;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
