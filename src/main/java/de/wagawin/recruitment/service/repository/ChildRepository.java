package de.wagawin.recruitment.service.repository;

import de.wagawin.recruitment.model.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildRepository extends JpaRepository<Child, Integer> {
    Child findFirstByNameAndAge(String name, Integer age);
}
