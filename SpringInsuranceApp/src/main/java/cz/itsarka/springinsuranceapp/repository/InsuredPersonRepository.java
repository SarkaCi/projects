package cz.itsarka.springinsuranceapp.repository;

import cz.itsarka.springinsuranceapp.entity.InsuredPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository pro entitu InsuredPerson.
 * Poskytuje metody pro základní operace s databází (CRUD) bez nutnosti psát vlastní implementaci.
 */
@Repository
public interface InsuredPersonRepository extends JpaRepository<InsuredPerson, Long> {
}
