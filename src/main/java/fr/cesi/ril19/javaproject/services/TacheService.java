package fr.cesi.ril19.javaproject.services;



import fr.cesi.ril19.javaproject.dao.TacheRepository;
import fr.cesi.ril19.javaproject.models.project.Project;
import fr.cesi.ril19.javaproject.models.project.Tache;
import fr.cesi.ril19.javaproject.models.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TacheService {
    TacheRepository taskRepo;

    @Autowired
    TacheService(TacheRepository taskRepo) {
        this.taskRepo = taskRepo;
    }

    public List<Tache> getTasks() {
        return (List<Tache>) this.taskRepo.findAll();
    }

    public List<Tache> findTasksByUser(User u){
        return  taskRepo.findByUser(u);
    }

    public List<Tache> findTasksByProject(Project p){
        return  taskRepo.findByIdprojet(p);
    }

    public String saveMultipleTasks(List<Tache> lu) {
        int count = 0;
        for (Tache u : lu) {
            try {
                this.taskRepo.save(u);
                count++;
            } catch (DataAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return count + " Task were added or modified.";
    }

    public String getTotalDays(){
        return taskRepo.getTotalDays();
    }

    public String getTotalCost(){
        return taskRepo.getTotalCost();
    }
}
