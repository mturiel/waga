package de.wagawin.recruitment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
public class Meal extends BaseEntity {
    private String name;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Child child;

    private Integer vote;
}
