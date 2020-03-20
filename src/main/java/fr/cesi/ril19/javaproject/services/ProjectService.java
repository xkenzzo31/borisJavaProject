package fr.cesi.ril19.javaproject.services;

import fr.cesi.ril19.javaproject.dao.ProjectRepository;
import fr.cesi.ril19.javaproject.exception.ProjectNotFoundException;
import fr.cesi.ril19.javaproject.models.project.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    ProjectRepository projectRepository;

    @Autowired
    ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getProjects() {
        return (List<Project>)this.projectRepository.findAll();
    }

    public Project findProjectById(Integer id) {
        return projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
    }

    public List<Project> searchByWord(String word) {
        return projectRepository.findByWord(word);
    }

    public Project saveProject(Project p) {
        try {
            return this.projectRepository.save(p);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public String saveMultipleProjects(List<Project> lp) {
        int count = 0;
        for (Project p : lp) {
            try {
                this.projectRepository.save(p);
                count++;
            } catch (DataAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return count + " Project were added or modified.";
    }

    public String deleteProject(Integer id) {
        String response = "Project " + id + "deleted";
        try {
            this.projectRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            response = "Project not found";
        }
        return response;
    }
}
