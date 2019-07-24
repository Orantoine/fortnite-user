package fr.Orantoine.fortniteuser.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import fr.Orantoine.fortniteuser.classes.MailHelper;
import fr.Orantoine.fortniteuser.exceptions.UserNotFoundException;
import fr.Orantoine.fortniteuser.models.Response;
import fr.Orantoine.fortniteuser.models.Session;
import fr.Orantoine.fortniteuser.models.User;
import fr.Orantoine.fortniteuser.repositories.SessionRepository;
import fr.Orantoine.fortniteuser.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URI;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailHelper mailHelper;

    @Autowired
    private SessionRepository sessionRepository;

    @GetMapping(value = "/health")
    public String HealthCheck(){
        return "OK";
    }


    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userRepository.findAll();
        return ResponseEntity.status(200).body(users);
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
    public ResponseEntity<User> findUser(@PathVariable String id){
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) throw new UserNotFoundException("L'utilisateur avec l'id "+ id + "est introuvable.");

        return ResponseEntity.status(200).body(user.get());
    }

    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id){
        userRepository.deleteById(id);
        return ResponseEntity.status(200).build();
    }
    @GetMapping(value = "/user/forget")
    public ResponseEntity<Void> forgetPassword(@RequestParam String email){
        User user = userRepository.findByEmail(email);
        boolean success = mailHelper.sendEmail(user.getEmail());
        if(success){
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(500).build();
    }

    @GetMapping(value = "/login")
    public ResponseEntity<Void> login(@RequestHeader(required =true) String pseudo, @RequestHeader(required = true) String password){
        Response response = new Response();
        User user = userRepository.findByPseudo(pseudo);
        if(user == null ){
            user = userRepository.findByEmail(pseudo);
        }
        if(user != null) {
            if(user.getPassword().equals(password)){
                response.setCode(200);
                response.setMessage("Vous êtes correctement idéntifié");
                Session session = new Session();
                session.setUser(user.getId());
                Date date = new Date();
                Timestamp ts=new Timestamp(date.getTime());
                session.setExpiration(ts);
                session.setToken(UUID.randomUUID().toString());
                sessionRepository.save(session);
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add("token",session.getToken());
                return ResponseEntity.status(200).headers(httpHeaders).build();
            }
        }
        return ResponseEntity.status(404).build();
    }
}
