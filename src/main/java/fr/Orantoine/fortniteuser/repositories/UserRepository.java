package fr.Orantoine.fortniteuser.repositories;

import fr.Orantoine.fortniteuser.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository  extends MongoRepository<User,String> {

    User findByEmail(String email);
    User findByPseudo(String pseudo);
}
