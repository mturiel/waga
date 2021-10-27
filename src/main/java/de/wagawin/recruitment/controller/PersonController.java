package de.wagawin.recruitment.controller;

import de.wagawin.recruitment.model.Person;
import de.wagawin.recruitment.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {
    @Autowired
    private PersonService personService;

    @PostMapping()
    public Person createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }

    @DeleteMapping("/{personId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable Integer personId) {
        personService.deletePerson(personId);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll() {
        personService.deleteAll();
    }

    @GetMapping()
    public List<Person> listAll() {
        return personService.getAll();
    }
}
