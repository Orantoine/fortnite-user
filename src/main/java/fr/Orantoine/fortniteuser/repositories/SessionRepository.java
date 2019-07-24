package fr.Orantoine.fortniteuser.repositories;

import fr.Orantoine.fortniteuser.models.Session;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SessionRepository extends MongoRepository<Session,String> {


}
