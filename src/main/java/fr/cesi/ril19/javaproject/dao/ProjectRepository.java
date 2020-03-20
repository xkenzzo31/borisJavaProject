package fr.cesi.ril19.javaproject.dao;


import fr.cesi.ril19.javaproject.models.project.Project;
import fr.cesi.ril19.javaproject.models.users.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Integer> {
    User findByid(Integer id);

    @Query("SELECT distinct(a) FROM Project a WHERE a.title like %:word% or a.description like %:word% order by a.budget desc")
    List<Project> findByWord(@Param("word") String word);
    //List<User> findByNumeroSuivi(String lastname);

}
