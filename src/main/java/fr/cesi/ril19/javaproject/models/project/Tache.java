package fr.cesi.ril19.javaproject.models.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.util.Collection;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tache")
public class Tache {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    private Project project;
    @Column(name = "num")
    private String num;
    @Column(name = "hourcost")
    private int hourCost;
    @Column(name = "duration")
    private int duration;

}
