package cz.itsarka.springinsuranceapp.service;

import cz.itsarka.springinsuranceapp.dto.InsuranceDTO;
import cz.itsarka.springinsuranceapp.entity.Insurance;
import cz.itsarka.springinsuranceapp.entity.InsuredPerson;
import cz.itsarka.springinsuranceapp.mapper.InsuranceMapper;
import cz.itsarka.springinsuranceapp.repository.InsuranceRepository;
import cz.itsarka.springinsuranceapp.repository.InsuredPersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Služba pro správu pojištění.
 * Poskytuje metody pro CRUD operace s entitou Insurance a správu přiřazení pojištění k pojištěným osobám.
 */
@Service
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final InsuredPersonRepository insuredPersonRepository;
    private final InsuranceMapper insuranceMapper;

    /**
     * Konstruktor pro injektování repository a inicializaci mapperu.
     *
     * @param insuranceRepository repository pro práci s pojištěními
     * @param insuredPersonRepository repository pro práci s pojištěnými osobami
     */
    public InsuranceService(InsuranceRepository insuranceRepository, InsuredPersonRepository insuredPersonRepository) {
        this.insuranceRepository = insuranceRepository;
        this.insuredPersonRepository = insuredPersonRepository;
        this.insuranceMapper = InsuranceMapper.INSTANCE;
    }

    /**
     * Získá seznam všech pojištění v databázi.
     * @return seznam InsuranceDTO
     */
    public List<InsuranceDTO> getAllInsurances() {
        return insuranceRepository.findAll()
                .stream()
                .map(InsuranceMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Najde pojištění podle jeho ID.
     * @param id ID pojištění
     * @return InsuranceDTO nebo null, pokud pojištění neexistuje
     */
    public InsuranceDTO getInsuranceById(Long id) {
        return insuranceRepository.findById(id)
                .map(insuranceMapper::toDTO)
                .orElse(null);
    }

    /**
     * Vytvoří nové pojištění a uloží ho do databáze.
     * Číslo pojistky je generováno automaticky v entitě.
     *
     * @param insuranceDTO data nového pojištění
     * @return vytvořené pojištění jako InsuranceDTO
     */
    public InsuranceDTO createInsurance(InsuranceDTO insuranceDTO) {
        Insurance insurance = InsuranceMapper.INSTANCE.toEntity(insuranceDTO);
        // Číslo pojistky se generuje automaticky v entitě
        Insurance savedInsurance = insuranceRepository.save(insurance);
        return InsuranceMapper.INSTANCE.toDTO(savedInsurance);
    }

    /**
     * Aktualizuje existující pojištění podle jeho ID.
     * @param id ID pojištění k aktualizaci
     * @param updatedInsurance nová data pojištění
     * @return aktualizované pojištění jako InsuranceDTO
     */
    public InsuranceDTO updateInsurance(Long id, InsuranceDTO updatedInsurance) {
        Insurance insurance = insuranceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pojištění nebylo nalezeno"));

        insurance.setType(updatedInsurance.getType());
        insurance.setAmount(updatedInsurance.getAmount());

        // ✅ Zachováme číslo pojistky (policyNumber)
        insuranceRepository.save(insurance);
        return insuranceMapper.toDTO(insurance);
    }

    /**
     * Odstraní pojištění podle jeho ID.
     * @param id ID pojištění k odstranění
     */
    public void deleteInsurance(Long id) {
        insuranceRepository.deleteById(id);
    }

    /**
     * Přiřadí pojištění pojištěné osobě.
     * @param insuredPersonId ID pojištěné osoby
     * @param insuranceId ID pojištění
     */
    public void assignInsuranceToPerson(Long insuredPersonId, Long insuranceId) {
        Optional<InsuredPerson> insuredPersonOpt = insuredPersonRepository.findById(insuredPersonId);
        Optional<Insurance> insuranceOpt = insuranceRepository.findById(insuranceId);

        if (insuredPersonOpt.isPresent() && insuranceOpt.isPresent()) {
            InsuredPerson insuredPerson = insuredPersonOpt.get();
            Insurance insurance = insuranceOpt.get();

            if (!insuredPerson.getInsurances().contains(insurance)) {
                insuredPerson.getInsurances().add(insurance);
                insuredPersonRepository.save(insuredPerson);
            }
        } else {
            throw new IllegalArgumentException("Pojištěná osoba nebo pojištění nebylo nalezeno.");
        }
    }

    /**
     * Odebere pojištění od pojištěné osoby.
     * @param insuredPersonId ID pojištěné osoby
     * @param insuranceId ID pojištění
     */
    public void removeInsuranceFromPerson(Long insuredPersonId, Long insuranceId) {
        Optional<InsuredPerson> insuredPersonOpt = insuredPersonRepository.findById(insuredPersonId);
        Optional<Insurance> insuranceOpt = insuranceRepository.findById(insuranceId);

        if (insuredPersonOpt.isPresent() && insuranceOpt.isPresent()) {
            InsuredPerson insuredPerson = insuredPersonOpt.get();
            Insurance insurance = insuranceOpt.get();

            if (insuredPerson.getInsurances().contains(insurance)) {
                insuredPerson.getInsurances().remove(insurance);
                insuredPersonRepository.save(insuredPerson);
            }
        } else {
            throw new IllegalArgumentException("Pojištěná osoba nebo pojištění nebylo nalezeno.");
        }
    }


}
