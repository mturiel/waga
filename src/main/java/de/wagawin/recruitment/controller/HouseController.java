package de.wagawin.recruitment.controller;

import de.wagawin.recruitment.model.House;
import de.wagawin.recruitment.model.Person;
import de.wagawin.recruitment.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/house")
public class HouseController {
    @Autowired
    private PersonService personService;

    @GetMapping()
    public House getHouseByPerson(@RequestBody Person person) {
        return personService.findPerson(person).getHouse();
    }
}
