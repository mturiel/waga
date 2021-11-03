package de.wagawin.recruitment.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter @Setter
@Entity
public class Daughter extends Child {
    private String hairColor;
}
