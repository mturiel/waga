package de.wagawin.recruitment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter @Setter
@Entity
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Child child;

    @Temporal(TemporalType.TIMESTAMP)
    private Date invented;

}
