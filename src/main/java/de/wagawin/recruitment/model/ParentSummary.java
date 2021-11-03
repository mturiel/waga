package de.wagawin.recruitment.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class ParentSummary extends BaseEntity {
    private Long amountOfPersons;

    private Long amountOfChildren;
}
