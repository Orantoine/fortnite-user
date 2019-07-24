package fr.Orantoine.fortniteuser.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Document(collection = "Session")
public class Session {


    @Id
    private String id;
    private String user;
    private String token;
    private Timestamp expiration;


    public Session(String id, String user, String token, Timestamp expiration) {
        this.id = id;
        this.user = user;
        this.token = token;
        this.expiration = expiration;
    }

    public Session() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getExpiration() {
        return expiration;
    }

    public void setExpiration(Timestamp expiration) {
        this.expiration = expiration;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id='" + id + '\'' +
                ", user='" + user + '\'' +
                ", token='" + token + '\'' +
                ", expiration='" + expiration + '\'' +
                '}';
    }
}
