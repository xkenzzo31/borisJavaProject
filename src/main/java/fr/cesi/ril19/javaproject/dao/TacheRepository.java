package fr.cesi.ril19.javaproject.dao;


import fr.cesi.ril19.javaproject.models.project.Project;
import fr.cesi.ril19.javaproject.models.project.Tache;
import fr.cesi.ril19.javaproject.models.users.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TacheRepository extends CrudRepository<Tache, Integer> {

    // findBy <=> Select from contacts where lastname
    List<Tache> findByUserId(User u);
    List<Tache> findByProject(Project idproject);

    @Query("select sum(duration)/7 from Tache group by project")
    String getTotalDays();

    @Query("select sum(duration*hourCost) from Tache group by project")
    String getTotalCost();


}
