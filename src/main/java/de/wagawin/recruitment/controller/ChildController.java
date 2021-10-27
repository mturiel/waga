package de.wagawin.recruitment.controller;

import de.wagawin.recruitment.dto.ParentMealDTO;
import de.wagawin.recruitment.model.Child;
import de.wagawin.recruitment.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/child")
public class ChildController {
    @Autowired
    private PersonService personService;

    @GetMapping("/info")
    public ParentMealDTO getChild(@RequestBody Child child) {
        return personService.findPersonByChild(child);
    }
}
