package de.wagawin.recruitment.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Son extends Child {
    private String bicycleColor;
}
