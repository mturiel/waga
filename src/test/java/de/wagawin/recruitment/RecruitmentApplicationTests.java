package de.wagawin.recruitment;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import de.wagawin.recruitment.controller.ChildController;
import de.wagawin.recruitment.controller.HouseController;
import de.wagawin.recruitment.controller.PersonController;
import de.wagawin.recruitment.dto.ParentMealDTO;
import de.wagawin.recruitment.model.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RecruitmentApplicationTests {
	@Autowired
	PersonController personController;

	@Autowired
	HouseController houseController;

	@Autowired
	ChildController childController;

	List<Person> personList = new ArrayList<>();

	static final long NUMBER_OF_PEOPLE = 10;
	static final int NUMBER_OF_CHILDREN_PER_PERSON = 3;
	static final int NUMBER_OF_MEAL_PER_CHILDREN = 2;
	static final int SIZE_LIMIT = 2;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(personController);
		Assertions.assertNotNull(houseController);
		Assertions.assertNotNull(childController);
	}

	@BeforeAll
	void beforeAll() {
		populateDatabase();

		// fetch all persons from database
		for (Person person : personController.listAll()) {
			// create children list
			List<Child> children = createChildren(NUMBER_OF_CHILDREN_PER_PERSON);

			// set all children to the person
			for (Child child : children) {
				if (child instanceof Son) {
					childController.createSon((Son) child, person.getId());
				} else {
					childController.createDaughter((Daughter) child, person.getId());
				}
			}
		}
	}

	@AfterAll
	void afterAll() {
		personController.deleteAll();
	}

	@Test
	void testGetHouse() {
		// fetch persons from database
		for (Person person : personController.getLimitedPersonList(SIZE_LIMIT)) {
			// check the house
			Assertions.assertNotNull(houseController.getHouseByPerson(person));
		}
	}

	@Test
	@Transactional
	void testGetChildInfo() {
		// fetch persons from database to check the child info
		for (Person person : personController.getLimitedPersonList(SIZE_LIMIT)) {
			for (Child child : person.getChildren()) {
				ParentMealDTO attachedChild = childController.getChild(child);
				Assertions.assertNotNull(attachedChild.getPerson());
				Assertions.assertNotNull(attachedChild.getChildFavoriteMeal());
			}
		}
	}

	@Test
	@Transactional
	void testGetColor() {
		// fetch persons from database to check the child info
		for (Person person : personController.getLimitedPersonList(SIZE_LIMIT)) {
			for (Child child : person.getChildren()) {
				ResponseEntity<Map<String, String>> color = childController.getColor(child);
				if (child instanceof Son) {
					Assertions.assertNotNull(color.getBody().get("bicycleColor"));
					Assertions.assertNull(color.getBody().get("hairColor"));
				} else {
					Assertions.assertNotNull(color.getBody().get("hairColor"));
					Assertions.assertNull(color.getBody().get("bicycleColor"));
				}
			}
		}
	}

	@Test
	void testParentSummary() {
		ParentSummary parentSummary = personController.getParentSummary();
		Assertions.assertNotNull(parentSummary);
	}

	private void populateDatabase() {
		for (int i = 0; i < NUMBER_OF_PEOPLE; i++) {
			personList.add(createPerson());
		}
		personList.forEach(person -> personController.createPerson(person));
	}

	private Person createPerson() {
		Lorem lorem = LoremIpsum.getInstance();
		Person person = new Person();

		person.setName(lorem.getFirstName());
		person.setAge(getRandomAgeParent());
		person.setHouse(createHouse());

		return person;
	}

	private House createHouse() {
		Lorem lorem = LoremIpsum.getInstance();
		House house = new House();

		house.setAddress(lorem.getCity());
		house.setZipCode(lorem.getZipCode());
		house.setType(HouseType.HOUSE);

		return house;
	}

	private List<Child> createChildren(int number) {
		Random random = new Random();
		Lorem lorem = LoremIpsum.getInstance();
		List<Child> children = new ArrayList<>();

		for (int i = 0; i < number; i++) {
			if (random.nextBoolean()) {
				Son son = new Son();
				son.setName(lorem.getFirstNameMale());
				son.setAge(getRandomAgeKids());
				son.setBicycleColor(lorem.getWords(1));
				son.setMealList(createMeal(NUMBER_OF_MEAL_PER_CHILDREN));

				children.add(son);
			} else {
				Daughter daughter = new Daughter();
				daughter.setName(lorem.getFirstNameFemale());
				daughter.setAge(getRandomAgeKids());
				daughter.setHairColor(lorem.getWords(1));
				daughter.setMealList(createMeal(NUMBER_OF_MEAL_PER_CHILDREN));

				children.add(daughter);
			}
		}

		return children;
	}

	private Set<Meal> createMeal(int number) {
		Lorem lorem = LoremIpsum.getInstance();
		Set<Meal> mealList = new HashSet<>();

		for (int i = 0; i < number; i++) {
			Meal meal = new Meal();
			meal.setName(lorem.getWords(1));
			meal.setVote(ThreadLocalRandom.current().nextInt(1, 11));

			mealList.add(meal);
		}

		return mealList;
	}

	private int getRandomAgeParent() {
		final int MIN_AGE = 18;
		final int MAX_AGE = 65;

		return ThreadLocalRandom.current().nextInt(MIN_AGE, MAX_AGE);
	}

	private int getRandomAgeKids() {
		final int MIN_AGE = 5;
		final int MAX_AGE = 17;

		return ThreadLocalRandom.current().nextInt(MIN_AGE, MAX_AGE);
	}

}
