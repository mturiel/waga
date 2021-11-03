package de.wagawin.recruitment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
public class House extends BaseEntity {
    private String address;

    private String zipCode;

    private HouseType type;

    @JsonIgnore
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Person person;

}
