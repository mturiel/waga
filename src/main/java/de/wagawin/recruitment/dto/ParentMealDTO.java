package de.wagawin.recruitment.dto;

import de.wagawin.recruitment.model.Meal;
import de.wagawin.recruitment.model.Person;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ParentMealDTO {
    private PersonDTO person;
    private String childFavoriteMeal;

    public ParentMealDTO(Person person, Meal meal) {
        this.person = new PersonDTO();
        this.person.setName(person.getName());
        this.person.setAge(person.getAge());
        this.childFavoriteMeal = meal.getName();
    }
}
