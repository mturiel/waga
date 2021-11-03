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

    /**
     * This endpoint returns the Parent and the most favorite meal of a child
     * @param child given child in json format. ID field is optional.
     * @return parent (person) and favorite meal DTO
     */
    @GetMapping("/info")
    public ParentMealDTO getChild(@RequestBody Child child) {
        return personService.findPersonByChild(child);
    }

    /**
     * This endpoint returns, for a given Child, the hairColor if the Child is a Daughter or
     * the bicycleColor if the Child is a Son
     * @param child given in json format. ID field is optional.
     * @return hairColor or bicycleColor
     */
    @GetMapping("/color")
    public ResponseEntity<Map<String, String>> getColor(@RequestBody Child child) {
        return personService.findChild(child);
    }

    /**
     * Create son for a given personId query param
     * @param son
     * @param personId
     * @return son json object
     */
    @PostMapping("/son")
    public Son createSon(@RequestBody Son son, @RequestParam Long personId) {
        return (Son) personService.createChild(son, personId);
    }

    /**
     * Create daughter for a given personId query param
     * @param daughter
     * @param personId
     * @return daughter json object
     */
    @PostMapping("/daughter")
    public Daughter createDaughter(@RequestBody Daughter daughter, @RequestParam Long personId) {
        return (Daughter) personService.createChild(daughter, personId);
    }
}
