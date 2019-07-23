package fr.Orantoine.fortniteuser.models;


import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;

@Document(collection = "User")
public class User {

    @Id
    private String id;
    private String pseudo;
    private String password;
    @Email
    private String email;
    private String platerform;

    public User(String id, String pseudo, String password, String email, String platerform) {
        this.id = id;
        this.pseudo = pseudo;
        this.password = password;
        this.email = email;
        this.platerform = platerform;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlaterform() {
        return platerform;
    }

    public void setPlaterform(String platerform) {
        this.platerform = platerform;
    }


    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", platerform='" + platerform + '\'' +
                '}';
    }
}
