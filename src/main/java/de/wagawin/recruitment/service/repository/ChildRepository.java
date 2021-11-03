package de.wagawin.recruitment.service.repository;

import de.wagawin.recruitment.model.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {
    Optional<Child> findFirstByNameAndAge(String name, Integer age);
}
