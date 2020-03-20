package fr.cesi.ril19.javaproject.route;

import fr.cesi.ril19.javaproject.models.project.Tache;
import fr.cesi.ril19.javaproject.models.users.User;
import fr.cesi.ril19.javaproject.services.ObjectJsonParser;
import fr.cesi.ril19.javaproject.services.TacheService;
import fr.cesi.ril19.javaproject.services.UserService;
import jdk.internal.jline.internal.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@RestController // Controller + ResponseBody
@RequestMapping("/api/v1/user")
public class UserController {

    private UserService userService;
    private TacheService taskService;


    @Autowired
    UserController(UserService userService, TacheService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @RequestMapping("")
    public List<User> getUsers() {
        return this.userService.getUsers();
    }

    @RequestMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        return this.userService.findUsersById(id);
    }

    @RequestMapping("/{id}/task")
    public ResponseEntity<?> getUsertask(@PathVariable Integer id) {
        List<Tache> tasks= this.taskService.findTasksByUser(getUserById(id));
        if (tasks.isEmpty()) {
            return new ResponseEntity<String>("No Tasks found for this user, you may want to synchronize the projects", HttpStatus.OK);
        }else{
            return new ResponseEntity<List<Tache>>(tasks, HttpStatus.OK);

        }
    }

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public ResponseEntity<User> postUser(@ModelAttribute("user") User user) {
        Preconditions.checkNotNull(user);
        return new ResponseEntity<User>(this.userService.saveUser(user), HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<User> updateUser(@ModelAttribute("user") User diffuser,
                                           @PathVariable Integer id) {
        diffuser.setId(id);
        return new ResponseEntity<User>(this.userService.saveUser(diffuser), HttpStatus.ACCEPTED);
    }


    @PostMapping("/import/csv")
    public ResponseEntity<?> uploadCSVFile(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        String message = "No new object added";
        if (file.isEmpty()) {
            return new ResponseEntity<String>("CSV file is empty, cannot create new users", HttpStatus.ACCEPTED);
        } else {
            InputStreamReader stream = new InputStreamReader(file.getInputStream());
            // convert `MultipartFile` object to list of user
            ObjectJsonParser ob = new ObjectJsonParser();
            List<Object> users = ob.parseFromCSV(stream, User.class);
            try  {
                // May not be a good way to do it.
                message = this.userService.saveMultipleUsers((List<User>) (Object)users);
            } catch (Exception ex) {
                return new ResponseEntity<String>("Could not map your csv to a User object : \n"+ ex, HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<String>(message, HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Integer id) {
        return new ResponseEntity<String>(this.userService.deleteUser(id), HttpStatus.ACCEPTED);
    }
}
