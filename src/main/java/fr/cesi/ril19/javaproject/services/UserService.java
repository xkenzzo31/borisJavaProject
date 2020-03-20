package fr.cesi.ril19.javaproject.services;


import fr.cesi.ril19.javaproject.dao.UserRepository;
import fr.cesi.ril19.javaproject.exception.UserNotFoundException;
import fr.cesi.ril19.javaproject.models.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.sql.SQLDataException;
import java.util.List;

@Service
public class UserService {
    UserRepository userRepo;

    @Autowired
    UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getUsers() {
        return (List<User>) this.userRepo.findAll();
    }


    public User findUsersById(Integer id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User findProjectByUserId(Integer id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }


    public User findUserTasks(Integer id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }





    public User saveUser(User u) {
        try {
            return this.userRepo.save(u);
        }catch (DataAccessException e){
            throw new RuntimeException(e);
        }
    }

    public String saveMultipleUsers(List<User> lu) {
        int count = 0;
        for (User u : lu) {
            try {
                this.userRepo.save(u);
                count++;
            } catch (DataAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return count + " User were added or modified.";
    }


    public String deleteUser(Integer id) {
        String response = "User " +id+"deleted";
        try {
            this.userRepo.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
            response = "User not found";
        }
        return response;
    }

}
