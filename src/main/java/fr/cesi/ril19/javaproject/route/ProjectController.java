package fr.cesi.ril19.javaproject.route;


import com.fasterxml.jackson.core.JsonProcessingException;
import fr.cesi.ril19.javaproject.models.project.Project;
import fr.cesi.ril19.javaproject.models.project.Tache;
import fr.cesi.ril19.javaproject.models.users.User;
import fr.cesi.ril19.javaproject.services.ObjectJsonParser;
import fr.cesi.ril19.javaproject.services.ProjectService;
import fr.cesi.ril19.javaproject.services.TacheService;
import fr.cesi.ril19.javaproject.services.UserService;

import jline.internal.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController // Controller + ResponseBody
@RequestMapping("/api/v1/project")
public class ProjectController {


    private ProjectService projectService;
    private TacheService taskService;
    private UserService userService;

    @Autowired
    public ProjectController(ProjectService projectService, TacheService taskService, UserService userService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.userService = userService;
    }

    // GET /project
    @RequestMapping("")
    public List<Project> getProjects() {
        return this.projectService.getProjects();
    }

    // GET /projet/1
    @RequestMapping("/{id}")
    public Project getProjectById(@PathVariable Integer id) {
        return this.projectService.findProjectById(id);
    }


    // GET /search/mot
    @RequestMapping("search/{mot}")
    public ResponseEntity<?> searchByWord(@PathVariable String mot) {
        List<Project> lp = this.projectService.searchByWord(mot);
        if (lp.isEmpty()) {
            return (ResponseEntity<?>) new ResponseEntity<String>("No Project Found", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<List<Project>>(lp, HttpStatus.OK);

        }
    }

    // GET /project/1/task - /project/id/task
    @RequestMapping("/{id}/task")
    public ResponseEntity<?> getProjectTask(@PathVariable Integer id) {
        List<Tache> tasks = this.taskService.findTasksByProject(getProjectById(id));
        if (tasks.isEmpty()) {
            return new ResponseEntity<String>("No Tasks found for this user, you may want to synchronize the projects", HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Tache>>(tasks, HttpStatus.OK);

        }
    }


    // GET /{id}}/sync
    @RequestMapping("{id}/sync")
    public ResponseEntity<?> syncProject(@PathVariable Integer id) throws JsonProcessingException {
        Project proj = getProjectById(id);
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = "https://afternoon-lake-76990.herokuapp.com/api/v1/timesheet/project/";
        String json = restTemplate.getForEntity(fooResourceUrl + "/" + proj.getId(), String.class).getBody();
        List<Tache> lt = ObjectJsonParser.parseTaskFromJSONString(json);
        lt = populateTask(lt);
        String message = taskService.saveMultipleTasks(lt);
        return new ResponseEntity<String>(message, HttpStatus.OK);
    }

    // GET /project/{id}/stats
    @RequestMapping("{id}/stats")
    public ResponseEntity<?> getstat(@PathVariable Integer id) {
        String message = "Couldnot find any task";
        Map<String, String> map = new HashMap<String, String>();
        Integer budget = Integer.valueOf(projectService.findProjectById(id).getBudget());
        try {
            map.put("totalDays", taskService.getTotalDays());
            String totalcost = taskService.getTotalCost();
            map.put("totalCost", totalcost);
            map.put("budgetRation", String.valueOf(Long.parseLong(totalcost) / budget * 100L));
            map.put("project",  Long.toString(id));

        } catch (Exception e) {
            message = e.getMessage();
            return new ResponseEntity<String>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
    }

    /**
     * add mockup value to task idProjet and user_id
     *
     * @param lt
     * @return the same but wirh the Tasks populated
     */
    public List<Tache> populateTask(List<Tache> lt) {
        //List<Task> populated = new ArrayList<Task>();
        List<User> lu = userService.getUsers();
        List<Project> lp = projectService.getProjects();

        for (Tache task : lt) {
            System.out.println(lu.size());
            task.setUser(lu.get(new Random().nextInt(lu.size())));
            task.setProject(lp.get(new Random().nextInt(lp.size())));
        }
        return lt;
    }

    // POST /import
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public ResponseEntity<Project> postProject(@RequestBody Project Project) {
        Preconditions.checkNotNull(Project);
        return new ResponseEntity<Project>(this.projectService.saveProject(Project), HttpStatus.CREATED);
    }

    @PostMapping("/import/csv")
    public ResponseEntity<?> uploadCSVFile(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        String message = "No new object added";
        if (file.isEmpty()) {
            return new ResponseEntity<String>("CSV file is empty, cannot create new projects", HttpStatus.NO_CONTENT);
        } else {
            InputStreamReader stream = new InputStreamReader(file.getInputStream());
            // convert `MultipartFile` object to list of project
            ObjectJsonParser ob = new ObjectJsonParser();
            List<Object> projects = ob.parseFromCSV(stream, Project.class);
            try {
                message = this.projectService.saveMultipleProjects((List<Project>) (Object) projects);
            } catch (Exception ex) {
                // TODO : May not want to retrun sql error to user
                return new ResponseEntity<String>("Could not map your csv to a Project object: \n" + ex, HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<String>(message, HttpStatus.ACCEPTED);
    }

    // UPDATE /update/id
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Project> updateProject(@ModelAttribute("Project") Project diffProject,
                                                 @PathVariable Integer id) {
        diffProject.setId(id);
        return new ResponseEntity<Project>(this.projectService.saveProject(diffProject), HttpStatus.ACCEPTED);
    }

    // DELETE /Project
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProjectById(@PathVariable Integer id) {
        return new ResponseEntity<String>(this.projectService.deleteProject(id), HttpStatus.ACCEPTED);
    }

}