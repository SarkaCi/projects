package cz.itsarka.springinsuranceapp.controller;

import cz.itsarka.springinsuranceapp.dto.InsuranceDTO;
import cz.itsarka.springinsuranceapp.dto.InsuredPersonDTO;
import cz.itsarka.springinsuranceapp.service.InsuredPersonService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST kontroler pro správu pojištěných osob.
 * Poskytuje CRUD operace a správu pojištění přiřazených osobám.
 */
@RestController
@RequestMapping("/api/insured-persons")
public class InsuredPersonController {

    private final InsuredPersonService insuredPersonService;

    /**
     * Konstruktor s injektovanou službou pro práci s pojištěnými osobami.
     */
    public InsuredPersonController(InsuredPersonService insuredPersonService) {
        this.insuredPersonService = insuredPersonService;
    }

    /**
     * Získá seznam všech pojištěných osob.
     * @return ResponseEntity obsahující seznam InsuredPersonDTO
     */
    @GetMapping
    public ResponseEntity<List<InsuredPersonDTO>> getAllInsuredPersons() {
        return ResponseEntity.ok(insuredPersonService.getAllInsuredPersons());
    }

    /**
     * Získá pojištěnou osobu podle jejího ID.
     * @param id ID pojištěné osoby
     * @return ResponseEntity obsahující InsuredPersonDTO nebo 404, pokud osoba neexistuje
     */
    @GetMapping("/{id}")
    public ResponseEntity<InsuredPersonDTO> getInsuredPersonById(@PathVariable Long id) {
        InsuredPersonDTO person = insuredPersonService.getInsuredPersonById(id);
        return person != null ? ResponseEntity.ok(person) : ResponseEntity.notFound().build();
    }

    /**
     * Vytvoří novou pojištěnou osobu.
     * @param insuredPersonDTO DTO obsahující data nové osoby
     * @return ResponseEntity obsahující vytvořenou osobu
     */
    @PostMapping
    public ResponseEntity<InsuredPersonDTO> createInsuredPerson(@Valid @RequestBody InsuredPersonDTO insuredPersonDTO) {
        return ResponseEntity.ok(insuredPersonService.createInsuredPerson(insuredPersonDTO));
    }

    /**
     * Aktualizuje existující pojištěnou osobu podle jejího ID.
     * @param id ID osoby k aktualizaci
     * @param insuredPersonDTO DTO s novými daty osoby
     * @return ResponseEntity obsahující aktualizovanou osobu nebo 404, pokud osoba neexistuje
     */
    @PutMapping("/{id}")
    public ResponseEntity<InsuredPersonDTO> updateInsuredPerson(@PathVariable Long id, @RequestBody @Valid InsuredPersonDTO insuredPersonDTO) {
        InsuredPersonDTO updated = insuredPersonService.updateInsuredPerson(id, insuredPersonDTO);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    /**
     * Odstraní pojištěnou osobu podle jejího ID.
     * @param id ID osoby k odstranění
     * @return ResponseEntity s HTTP statusem 204 (No Content) nebo 404, pokud osoba neexistuje
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInsuredPerson(@PathVariable Long id) {
        if (insuredPersonService.getInsuredPersonById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        insuredPersonService.deleteInsuredPerson(id);
        return ResponseEntity.noContent().build();
    }


    /**
     * Přiřadí pojištění pojištěné osobě s možností nastavení platnosti pojištění.
     * @param id ID pojištěné osoby
     * @param insuranceId ID pojištění
     * @param insuranceDTO DTO obsahující data o platnosti pojištění
     * @return ResponseEntity obsahující aktualizovanou pojištěnou osobu nebo chybovou zprávu
     */
    @PostMapping("/{id}/assign-insurance/{insuranceId}")
    public ResponseEntity<?> assignInsurance(
            @PathVariable Long id,
            @PathVariable Long insuranceId,
            @RequestBody InsuranceDTO insuranceDTO) {  // Přidání platnosti pojištění
        try {
            InsuredPersonDTO updatedPerson = insuredPersonService.assignInsuranceToPerson(
                    id,
                    insuranceId,
                    insuranceDTO.getValidFrom(),
                    insuranceDTO.getValidTo()
            );
            return ResponseEntity.ok(updatedPerson);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Odebere pojištění od pojištěné osoby.
     * @param id ID pojištěné osoby
     * @param insuranceId ID pojištění
     * @return ResponseEntity obsahující aktualizovanou pojištěnou osobu nebo chybovou zprávu
     */
    @DeleteMapping("/{id}/remove-insurance/{insuranceId}")
    public ResponseEntity<?> removeInsurance(@PathVariable Long id, @PathVariable Long insuranceId) {
        try {
            InsuredPersonDTO updatedPerson = insuredPersonService.removeInsuranceFromPerson(id, insuranceId);
            return ResponseEntity.ok(updatedPerson);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Získá seznam pojištění přiřazených konkrétní pojištěné osobě.
     * @param id ID pojištěné osoby
     * @return ResponseEntity obsahující seznam pojištění nebo 404, pokud osoba neexistuje
     */
    @GetMapping("/{id}/insurances")
    public ResponseEntity<List<InsuranceDTO>> getInsurancesForPerson(@PathVariable Long id) {
        InsuredPersonDTO person = insuredPersonService.getInsuredPersonById(id);

        if (person == null) {
            return ResponseEntity.notFound().build();
        }

        List<InsuranceDTO> insurances = insuredPersonService.getInsurancesForPerson(person.getInsuranceIds());
        return ResponseEntity.ok(insurances);
    }

   
}
