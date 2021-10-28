package de.wagawin.recruitment.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "UniqueNameAndAge", columnNames = { "name", "age" }) })
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private Integer age;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private House house;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Child> children = new HashSet<>();

    public void addChild(Child child) {
        child.setPerson(this);
        children.add(child);
    }

}
