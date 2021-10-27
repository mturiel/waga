package de.wagawin.recruitment.service;

import de.wagawin.recruitment.dto.ParentMealDTO;
import de.wagawin.recruitment.model.Child;
import de.wagawin.recruitment.model.Meal;
import de.wagawin.recruitment.model.Person;
import de.wagawin.recruitment.service.repository.ChildRepository;
import de.wagawin.recruitment.service.repository.HouseRepository;
import de.wagawin.recruitment.service.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private HouseRepository houseRepository;

    public Person createPerson(Person person) {
        person.getHouse().setPerson(person);

        for (Child child : person.getChildren()) {
            person.addChild(child);

            for (Meal meal : child.getMealList()) {
                child.addMeal(meal);
            }
        }

        return personRepository.save(person);
    }

    public void deletePerson(Integer id) {
        personRepository.deleteById(id);
    }

    public List<Person> getAll() {
        return personRepository.findAll();
    }

    public Person findPerson(Person person) {
        return personRepository.findFirstByNameAndAge(person.getName(), person.getAge());
    }

    public ParentMealDTO findPersonByChild(Child child) {
        Child attached = childRepository.findFirstByNameAndAge(child.getName(), child.getAge());
        Optional<Meal> favoriteMeal = attached.getMealList().stream().findFirst();
        return new ParentMealDTO(attached.getPerson(), favoriteMeal.get());
    }

    public void deleteAll() {
        personRepository.deleteAll();
    }
}
