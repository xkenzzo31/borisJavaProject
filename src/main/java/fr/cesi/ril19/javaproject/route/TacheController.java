package fr.cesi.ril19.javaproject.route;


import fr.cesi.ril19.javaproject.models.project.Tache;
import fr.cesi.ril19.javaproject.services.TacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // Controller + ResponseBody
@RequestMapping("/api/v1/task")
public class TacheController {


    private TacheService taskService;

    @Autowired
    TacheController(TacheService taskService) {
        this.taskService = taskService;
    }

    // GET /contacts
    @RequestMapping("")
    public List<Tache> getTasks() {
        return this.taskService.getTasks();
    }



}