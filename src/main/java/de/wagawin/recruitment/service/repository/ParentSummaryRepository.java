package de.wagawin.recruitment.service.repository;


import de.wagawin.recruitment.model.ParentSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentSummaryRepository extends JpaRepository<ParentSummary, Long> {
}
