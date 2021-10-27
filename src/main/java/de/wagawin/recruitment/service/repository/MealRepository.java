package de.wagawin.recruitment.service.repository;

import de.wagawin.recruitment.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends JpaRepository<Meal, Integer> {
}
