package fr.cesi.ril19.javaproject.models.project;

import fr.cesi.ril19.javaproject.dao.UserRepository;
import fr.cesi.ril19.javaproject.models.users.Manager;
import fr.cesi.ril19.javaproject.models.users.User;
import lombok.*;
import org.hibernate.annotations.ManyToAny;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import javax.websocket.OnError;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false,updatable = false)
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @OneToMany
    private Collection<User> users;
    @Column(name = "budget")
    private int budget;
    @Column(name = "startdate")
    private long startDate;
    @Column(name = "workdays")
    private int workDays;
    @OneToMany
    private Set<Tache> taches;
}
