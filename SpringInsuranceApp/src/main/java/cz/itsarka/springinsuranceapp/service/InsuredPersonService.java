package cz.itsarka.springinsuranceapp.service;

import cz.itsarka.springinsuranceapp.dto.AddressDTO;
import cz.itsarka.springinsuranceapp.dto.InsuranceDTO;
import cz.itsarka.springinsuranceapp.dto.InsuredPersonDTO;
import cz.itsarka.springinsuranceapp.entity.Address;
import cz.itsarka.springinsuranceapp.entity.Insurance;
import cz.itsarka.springinsuranceapp.entity.InsuredPerson;
import cz.itsarka.springinsuranceapp.mapper.InsuranceMapper;
import cz.itsarka.springinsuranceapp.mapper.InsuredPersonMapper;
import cz.itsarka.springinsuranceapp.repository.InsuranceRepository;
import cz.itsarka.springinsuranceapp.repository.InsuredPersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Služba pro správu pojištěných osob.
 * Poskytuje CRUD operace a správu přiřazených pojištění.
 */
@Service
public class InsuredPersonService {

    private final InsuredPersonRepository insuredPersonRepository;
    private final InsuranceRepository insuranceRepository;
    private final InsuredPersonMapper insuredPersonMapper;

    /**
     * Konstruktor pro injektování repository a inicializaci mapperu.
     *
     * @param insuredPersonRepository repository pro práci s pojištěnými osobami
     * @param insuranceRepository repository pro práci s pojištěními
     */
    public InsuredPersonService(InsuredPersonRepository insuredPersonRepository, InsuranceRepository insuranceRepository) {
        this.insuredPersonRepository = insuredPersonRepository;
        this.insuranceRepository = insuranceRepository;
        this.insuredPersonMapper = InsuredPersonMapper.INSTANCE;
    }

    /**
     * Získá seznam všech pojištěných osob.
     * @return seznam InsuredPersonDTO
     */
    public List<InsuredPersonDTO> getAllInsuredPersons() {
        return insuredPersonRepository.findAll()
                .stream()
                .map(insuredPersonMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Najde pojištěnou osobu podle jejího ID.
     * @param id ID pojištěné osoby
     * @return InsuredPersonDTO nebo null, pokud osoba neexistuje
     */
    public InsuredPersonDTO getInsuredPersonById(Long id) {
        return insuredPersonRepository.findById(id)
                .map(insuredPersonMapper::toDTO)
                .orElse(null);
    }

    /**
     * Vytvoří novou pojištěnou osobu a uloží ji do databáze.
     * @param insuredPersonDTO data nové osoby
     * @return vytvořená osoba jako InsuredPersonDTO
     */
    public InsuredPersonDTO createInsuredPerson(InsuredPersonDTO insuredPersonDTO) {
        InsuredPerson person = insuredPersonMapper.toEntity(insuredPersonDTO);
        return insuredPersonMapper.toDTO(insuredPersonRepository.save(person));
    }

    /**
     * Aktualizuje existující pojištěnou osobu podle jejího ID.
     * @param id ID osoby k aktualizaci
     * @param insuredPersonDTO nové hodnoty pro osobu
     * @return aktualizovaná osoba jako InsuredPersonDTO nebo null, pokud osoba neexistuje
     */
    public InsuredPersonDTO updateInsuredPerson(Long id, InsuredPersonDTO insuredPersonDTO) {
        Optional<InsuredPerson> optionalInsuredPerson = insuredPersonRepository.findById(id);

        if (optionalInsuredPerson.isPresent()) {
            InsuredPerson insuredPerson = optionalInsuredPerson.get();

            if (insuredPersonDTO.getDateOfBirth() != null) {
                insuredPerson.setDateOfBirth(insuredPersonDTO.getDateOfBirth());
            }

            insuredPerson.setFirstName(insuredPersonDTO.getFirstName());
            insuredPerson.setLastName(insuredPersonDTO.getLastName());
            insuredPerson.setEmail(insuredPersonDTO.getEmail());
            insuredPerson.setPhone(insuredPersonDTO.getPhone());

            // Aktualizace adresy
            if (insuredPersonDTO.getAddress() != null) {
                if (insuredPerson.getAddress() == null) {
                    insuredPerson.setAddress(new Address());
                }
                Address address = insuredPerson.getAddress();
                AddressDTO addressDTO = insuredPersonDTO.getAddress();

                address.setStreet(addressDTO.getStreet());
                address.setStreetNumber(addressDTO.getStreetNumber());
                address.setCity(addressDTO.getCity());
                address.setPostalCode(addressDTO.getPostalCode());
                address.setCountry(addressDTO.getCountry());
            }

            InsuredPerson updatedInsuredPerson = insuredPersonRepository.save(insuredPerson);
            return insuredPersonMapper.toDTO(updatedInsuredPerson);
        }
        return null;
    }

    /**
     * Odstraní pojištěnou osobu podle jejího ID.
     * @param id ID osoby k odstranění
     */
    public void deleteInsuredPerson(Long id) {
        if (!insuredPersonRepository.existsById(id)) {
            throw new EntityNotFoundException("Pojištěná osoba s ID " + id + " neexistuje.");
        }
        insuredPersonRepository.deleteById(id);
    }

    /**
     * Přiřadí pojištění pojištěné osobě s možností nastavení platnosti pojištění.
     * @param personId ID pojištěné osoby
     * @param insuranceId ID pojištění
     * @param validFrom datum začátku platnosti pojištění
     * @param validTo datum konce platnosti pojištění
     * @return aktualizovaná pojištěná osoba jako InsuredPersonDTO
     */
    public InsuredPersonDTO assignInsuranceToPerson(Long personId, Long insuranceId, LocalDate validFrom, LocalDate validTo) {
        InsuredPerson person = insuredPersonRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Pojištěná osoba nebyla nalezena"));

        Insurance insurance = insuranceRepository.findById(insuranceId)
                .orElseThrow(() -> new RuntimeException("Pojištění nebylo nalezeno"));

        if (!person.getInsurances().contains(insurance)) {
            // Nastavíme platnost pro konkrétní osobu
            insurance.setValidFrom(validFrom);
            insurance.setValidTo(validTo);

            person.getInsurances().add(insurance);
            insuredPersonRepository.save(person);
        }

        return insuredPersonMapper.toDTO(person);
    }

    /**
     * Odebere pojištění od pojištěné osoby.
     * @param personId ID pojištěné osoby
     * @param insuranceId ID pojištění
     * @return aktualizovaná pojištěná osoba jako InsuredPersonDTO
     */
    public InsuredPersonDTO removeInsuranceFromPerson(Long personId, Long insuranceId) {
        InsuredPerson person = insuredPersonRepository.findById(personId)
                .orElseThrow(() -> new EntityNotFoundException("Pojištěná osoba nenalezena."));
        Insurance insurance = insuranceRepository.findById(insuranceId)
                .orElseThrow(() -> new EntityNotFoundException("Pojištění nenalezeno."));

        if (person.getInsurances().contains(insurance)) {
            person.getInsurances().remove(insurance);
            insuredPersonRepository.save(person);
        }

        return insuredPersonMapper.toDTO(person);
    }

    /**
     * Získá seznam pojištění přiřazených pojištěné osobě.
     * @param insuranceIds seznam ID pojištění
     * @return seznam InsuranceDTO odpovídajících pojištěním
     */
    public List<InsuranceDTO> getInsurancesForPerson(List<Long> insuranceIds) {
        if (insuranceIds == null || insuranceIds.isEmpty()) {
            return List.of(); // Vrátí prázdný seznam, pokud nejsou žádná pojištění
        }

        return insuranceRepository.findByIdIn(insuranceIds)
                .stream()
                .map(InsuranceMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

}
