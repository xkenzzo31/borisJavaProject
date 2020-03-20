package fr.cesi.ril19.javaproject.route;

import fr.cesi.ril19.javaproject.dao.UserRepository;
import fr.cesi.ril19.javaproject.models.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    private void saveUser(@RequestBody User user){
        userRepository.save(user);
    }
    @RequestMapping("/findAll")
    private Iterable<User> test(){
        return userRepository.findAll();
    }
}
