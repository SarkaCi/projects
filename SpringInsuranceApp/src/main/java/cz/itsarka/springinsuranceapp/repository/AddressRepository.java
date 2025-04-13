package cz.itsarka.springinsuranceapp.repository;

import cz.itsarka.springinsuranceapp.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository pro entitu Address.
 * Poskytuje metody pro základní operace s databází (CRUD) bez nutnosti psát vlastní implementaci.
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}