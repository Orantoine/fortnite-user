package fr.Orantoine.fortniteuser.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import fr.Orantoine.fortniteuser.classes.MailHelper;
import fr.Orantoine.fortniteuser.exceptions.UserNotFoundException;
import fr.Orantoine.fortniteuser.models.Response;
import fr.Orantoine.fortniteuser.models.User;
import fr.Orantoine.fortniteuser.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailHelper mailHelper;

    @GetMapping(value = "/health")
    public String HealthCheck(){
        return "OK";
    }


    @GetMapping(value = "/users")
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    @PostMapping(value = "/user")
    public ResponseEntity<Void> addUser(@RequestBody User user){
        User userAdded = userRepository.save(user);
        if(userAdded == null ) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(201).build();
    }

    @GetMapping(value = "/user/{id}")
    public Optional<User> findUser(@PathVariable String id){
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) throw new UserNotFoundException("L'utilisateur avec l'id "+ id + "est introuvable.");

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
        mailHelper.sendEmail(user.getEmail());
        return user;
    }

    @GetMapping(value = "/login")
    public String login(@RequestHeader(required =true) String pseudo, @RequestHeader(required = true) String password){
        Response response = new Response();
        User user = userRepository.findByPseudo(pseudo);
        if(user == null ){
            user = userRepository.findByEmail(pseudo);
        }
        if(user != null) {
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
