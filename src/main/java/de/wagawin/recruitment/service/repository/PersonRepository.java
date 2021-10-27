package de.wagawin.recruitment.service.repository;

import de.wagawin.recruitment.model.House;
import de.wagawin.recruitment.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person findFirstByNameAndAge(String name, Integer age);
}
