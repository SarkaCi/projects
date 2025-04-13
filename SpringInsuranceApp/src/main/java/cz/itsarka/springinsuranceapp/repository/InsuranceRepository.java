package cz.itsarka.springinsuranceapp.repository;

import cz.itsarka.springinsuranceapp.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA repository pro entitu Insurance.
 * Poskytuje metody pro základní operace s databází (CRUD) bez nutnosti psát vlastní implementaci.
 */
@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Long> {

    /**
     * Najde seznam pojištění podle jejich ID.
     * Používá se například pro získání pojištění přiřazených konkrétní pojištěné osobě.
     *
     * @param ids seznam ID pojištění
     * @return seznam entit Insurance odpovídajících zadaným ID
     */
    List<Insurance> findByIdIn(List<Long> ids);
}


