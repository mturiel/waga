package de.wagawin.recruitment.controller;

import de.wagawin.recruitment.dto.ParentMealDTO;
import de.wagawin.recruitment.model.Child;
import de.wagawin.recruitment.model.Daughter;
import de.wagawin.recruitment.model.Son;
import de.wagawin.recruitment.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/child")
public class ChildController {
    @Autowired
    private PersonService personService;

    @GetMapping("/info")
    public ParentMealDTO getChild(@RequestBody Child child) {
        return personService.findPersonByChild(child);
    }

    @GetMapping("/color")
    public ResponseEntity<Map<String, String>> getColor(@RequestBody Child child) {
        return personService.findChild(child);
    }

    @PostMapping("/son")
    public Son createSon(@RequestBody Son son, @RequestParam Integer personId) {
        return (Son) personService.createChild(son, personId);
    }

    @PostMapping("/daughter")
    public Daughter createDaughter(@RequestBody Daughter daughter, @RequestParam Integer personId) {
        return (Daughter) personService.createChild(daughter, personId);
    }
}
