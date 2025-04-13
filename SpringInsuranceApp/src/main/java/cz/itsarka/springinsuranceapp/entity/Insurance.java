package cz.itsarka.springinsuranceapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Entita reprezentující pojištění. Obsahuje informace o čísle pojistky, typu,
 * částce, platnosti a seznamu pojištěných osob.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "insurances")
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primární klíč, automaticky generovaný

    @NotBlank(message = "Číslo pojistky nesmí být prázdné")
    private String policyNumber; // Unikátní číslo pojistky

    @NotBlank(message = "Typ pojištění nesmí být prázdný")
    private String type; // Typ pojištění (např. životní, cestovní)

    @Min(value = 0, message = "Částka pojištění nesmí být záporná")
    private double amount; // Pojistná částka

    private LocalDate validFrom;  // Datum začátku platnosti pojištění
    private LocalDate validTo; // Datum konce platnosti pojištění

    private boolean template; // True = šablona, False = konkrétní pojištění


    @ManyToMany(mappedBy = "insurances")
    private List<InsuredPerson> insuredPersons; // Seznam pojištěných osob

    /**
     * Generuje unikátní číslo pojistky před uložením do databáze.
     * - Pokud je pojištění šablona, přidá prefix "T-".
     * - Pokud jde o konkrétní pojistku, přidá prefix "P-".
     */
    @PrePersist
    public void generatePolicyNumber() {
        if (this.policyNumber == null || this.policyNumber.isEmpty()) {
            if (this.template) {
                this.policyNumber = "T-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase(); // Šablony mají prefix "T-"
            } else {
                this.policyNumber = "P-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase(); // Konkrétní pojistky mají prefix "P-"
            }
        }
    }
}
