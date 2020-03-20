package fr.cesi.ril19.javaproject.dao;

import fr.cesi.ril19.javaproject.models.users.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findById(Integer integer);
}
