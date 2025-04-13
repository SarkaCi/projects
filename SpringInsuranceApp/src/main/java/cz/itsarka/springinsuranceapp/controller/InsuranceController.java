package cz.itsarka.springinsuranceapp.controller;

import cz.itsarka.springinsuranceapp.dto.InsuranceDTO;
import cz.itsarka.springinsuranceapp.service.InsuranceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST kontroler pro správu pojištění.
 * Poskytuje CRUD operace pro entity Insurance a umožňuje přiřazování pojištění k pojištěným osobám.
 */
@RestController
@RequestMapping("/api/insurances")
public class InsuranceController {

    private final InsuranceService insuranceService;

    /**
     * Konstruktor s injektovanou službou pro práci s pojištěním.
     */
    public InsuranceController(InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }

    /**
     * Získá seznam všech pojištění.
     * @return ResponseEntity obsahující seznam InsuranceDTO
     */
    @GetMapping
    public ResponseEntity<List<InsuranceDTO>> getAllInsurances() {
        return ResponseEntity.ok(insuranceService.getAllInsurances());
    }

    /**
     * Získá pojištění podle jeho ID.
     * @param id ID pojištění
     * @return ResponseEntity obsahující InsuranceDTO nebo 404, pokud pojištění neexistuje
     */
    @GetMapping("/{id}")
    public ResponseEntity<InsuranceDTO> getInsuranceById(@PathVariable Long id) {
        InsuranceDTO insurance = insuranceService.getInsuranceById(id);
        if (insurance == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(insurance);
    }

    /**
     * Vytvoří nové pojištění.
     * @param insuranceDTO DTO obsahující data nového pojištění
     * @return ResponseEntity obsahující vytvořené pojištění
     */
    @PostMapping
    public ResponseEntity<InsuranceDTO> createInsurance(@Valid @RequestBody InsuranceDTO insuranceDTO) {
        return ResponseEntity.ok(insuranceService.createInsurance(insuranceDTO));
    }

    /**
     * Aktualizuje existující pojištění podle jeho ID.
     * @param id ID pojištění k aktualizaci
     * @param insuranceDTO DTO s novými daty pojištění
     * @return ResponseEntity obsahující aktualizované pojištění nebo 404, pokud pojištění neexistuje
     */
    @PutMapping("/{id}")
    public ResponseEntity<InsuranceDTO> updateInsurance(@PathVariable Long id, @RequestBody InsuranceDTO insuranceDTO) {
        InsuranceDTO updated = insuranceService.updateInsurance(id, insuranceDTO);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    /**
     * Odstraní pojištění podle jeho ID.
     * @param id ID pojištění k odstranění
     * @return ResponseEntity s informací o úspěšném nebo neúspěšném smazání
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInsurance(@PathVariable Long id) {
        try {
            insuranceService.deleteInsurance(id);
            return ResponseEntity.ok("Pojištění bylo úspěšně smazáno.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body("Nelze smazat pojištění, protože je přiřazeno osobě.");
        }
    }

    /**
     * Přiřadí pojištění k pojištěné osobě.
     * @param insuredPersonId ID pojištěné osoby
     * @param insuranceId ID pojištění
     * @return ResponseEntity s informací o úspěšném nebo neúspěšném přiřazení
     */
    @PostMapping("/{insuredPersonId}/assign/{insuranceId}")
    public ResponseEntity<String> assignInsurance(@PathVariable Long insuredPersonId, @PathVariable Long insuranceId) {
        try {
            insuranceService.assignInsuranceToPerson(insuredPersonId, insuranceId);
            return ResponseEntity.ok("Pojištění úspěšně přiřazeno.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Odebere pojištění od pojištěné osoby.
     * @param insuredPersonId ID pojištěné osoby
     * @param insuranceId ID pojištění
     * @return ResponseEntity s informací o úspěšném nebo neúspěšném odebrání
     */
    @DeleteMapping("/{insuredPersonId}/remove/{insuranceId}")
    public ResponseEntity<String> removeInsurance(@PathVariable Long insuredPersonId, @PathVariable Long insuranceId) {
        try {
            insuranceService.removeInsuranceFromPerson(insuredPersonId, insuranceId);
            return ResponseEntity.ok("Pojištění úspěšně odebráno.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
