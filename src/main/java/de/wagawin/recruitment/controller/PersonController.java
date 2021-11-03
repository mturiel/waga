package de.wagawin.recruitment.controller;

import de.wagawin.recruitment.model.ParentSummary;
import de.wagawin.recruitment.model.Person;
import de.wagawin.recruitment.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {
    @Autowired
    private PersonService personService;

    /**
     * This endpoint returns a summary of n people who have n children
     * @return parent summary json object
     */
    @GetMapping("/children")
    public ParentSummary getParentSummary() {
        return personService.getSummary();
    }

    /**
     * Create person
     * @param person person json object containing house and children list
     * @return created person
     */
    @PostMapping
    public Person createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }

    /**
     * List all people in database
     * @return person list
     */
    @GetMapping
    public List<Person> listAll() {
        return personService.getAll();
    }

    /**
     * List limited people in database for testing only
     * @return person list
     */
    public List<Person> getLimitedPersonList(int size) {
        return personService.getLimitedPersonList(size);
    }

    /**
     * Delete a given person by personId
     * @param personId
     */
    @DeleteMapping("/{personId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable Long personId) {
        personService.deletePerson(personId);
    }

    /**
     * Delete people table
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll() {
        personService.deleteAll();
    }
}
