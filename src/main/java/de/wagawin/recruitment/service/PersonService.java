package de.wagawin.recruitment.service;

import de.wagawin.recruitment.dto.ParentMealDTO;
import de.wagawin.recruitment.exceptions.InternalException;
import de.wagawin.recruitment.exceptions.NotFoundException;
import de.wagawin.recruitment.model.*;
import de.wagawin.recruitment.service.repository.ChildRepository;
import de.wagawin.recruitment.service.repository.ParentSummaryRepository;
import de.wagawin.recruitment.service.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class PersonService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private ParentSummaryRepository parentSummaryRepository;

    /**
     * Returns a given person
     * @param person given person with ID optional
     * @return person
     */
    public Person findPerson(Person person) {
        Optional<Person> attached;
        if (person.getId() == null) {
            // find by name and age if ID was not provided
            attached = personRepository.findFirstByNameAndAge(person.getName(), person.getAge());
        } else {
            // find only by ID
            attached = personRepository.findById(person.getId());
        }

        return attached.orElseThrow(NotFoundException::new);
    }

    /**
     * Returns the Parent and the most favorite meal of a child
     * @param child given child object with ID optional
     * @return parent (person) and favorite meal DTO
     */
    public ParentMealDTO findPersonByChild(Child child) {
        Optional<Child> attached;
        if (child.getId() == null) {
            // find by name and age if ID was not provided
            attached = childRepository.findFirstByNameAndAge(child.getName(), child.getAge());
        } else {
            // find only by ID
            attached = childRepository.findById(child.getId());
        }

        Child kid = attached.orElseThrow(NotFoundException::new);

        // Get the maximum vote. Higher the vote, more favorite is the meal.
        Optional<Meal> favoriteMeal = kid.getMealList()
                .stream()
                .max(Comparator.comparingInt(Meal::getVote));

        return favoriteMeal
                .map(meal -> new ParentMealDTO(kid.getPerson(), meal))
                .orElseGet(() -> new ParentMealDTO(kid.getPerson(), new Meal()));
    }

    /**
     * For a given Child, the hairColor if the Child is a Daughter or
     * the bicycleColor if the Child is a Son.
     * @param child given child daughter or son
     * @return json response
     */
    public ResponseEntity<Map<String, String>> findChild(Child child) {
        Optional<Child> attached;
        if (child.getId() == null) {
            // find by name and age if ID was not provided
            attached = childRepository.findFirstByNameAndAge(child.getName(), child.getAge());
        } else {
            // find only by ID
            attached = childRepository.findById(child.getId());
        }

        Map<String, String> colorMap = attached.map(kid -> {
            String key, value;
            if (kid instanceof Son) {
                key = "bicycleColor";
                value = ((Son) kid).getBicycleColor();
            } else if (kid instanceof Daughter) {
                key = "hairColor";
                value = ((Daughter) kid).getHairColor();
            } else {
                throw new InternalException("Child is not of type son or daughter");
            }
            return Collections.singletonMap(key, value);
        }).orElseThrow(NotFoundException::new);

        return ResponseEntity.ok(colorMap);
    }

    synchronized public ParentSummary getSummary() {
        List<ParentSummary> summaryList = parentSummaryRepository.findAll();
        if (summaryList.size() > 0) {
            // There should be only one summary in table
            return summaryList.get(0);
        }

        return new ParentSummary();
    }

    /**
     * Runs every 15 minutes to calculate number of parents having n children
     */
    @Transactional
    @Scheduled(fixedDelay = 15, timeUnit = TimeUnit.MINUTES)
    public void calculatePersonsChildren() {
        final long startTime = System.currentTimeMillis();

        long amountOfPerson = 0L;
        long amountOfChildren = 0L;
        int page = 0;
        final int size = 1000;

        Slice<Person> personSliced = personRepository.findAll(PageRequest.of(page, size));
        boolean hasContent = personSliced.hasContent();

        while (hasContent) {
            List<Person> people = personSliced.getContent();
            for (Person person : people) {
                if (person.getChildren().size() > 0) {
                    amountOfPerson++;
                    amountOfChildren += person.getChildren().size();
                }
            }
            personSliced = personRepository.findAll(PageRequest.of(++page, size));
            hasContent = personSliced.hasContent();
        }

        ParentSummary parentSummary = getSummary();
        parentSummary.setAmountOfPersons(amountOfPerson);
        parentSummary.setAmountOfChildren(amountOfChildren);
        parentSummaryRepository.save(parentSummary);

        logger.info("CalculatePersonsChildren in {} ms", (System.currentTimeMillis() - startTime));
    }

    /**
     * Supporting methods not direct required in test
     */
    public List<Person> getAll() {
        return personRepository.findAll();
    }

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

    public Child createChild(Child child, Long personId) {
        Optional<Person> attached = personRepository.findById(personId);

        return attached.map(person -> {
            child.setPerson(person);
            for (Meal meal : child.getMealList()) {
                child.addMeal(meal);
            }
            return childRepository.save(child);
        }).orElseThrow(NotFoundException::new);
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    public void deleteAll() {
        personRepository.deleteAll();
    }

    public List<Person> getLimitedPersonList(int size) {
        return personRepository.findAll(PageRequest.of(0, size)).getContent();
    }
}
