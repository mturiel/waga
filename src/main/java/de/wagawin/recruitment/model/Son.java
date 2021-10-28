package de.wagawin.recruitment.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public class Son extends Child {
    private String bicycleColor;
}
