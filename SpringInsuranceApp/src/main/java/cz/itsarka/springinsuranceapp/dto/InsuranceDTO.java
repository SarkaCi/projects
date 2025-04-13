package cz.itsarka.springinsuranceapp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Data Transfer Object (DTO) pro přenos informací o pojištění mezi frontendem a backendem.
 * DTO slouží k validaci vstupních dat a ochraně vnitřní struktury aplikace.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceDTO {

    private Long id; // ID pojištění, používá se pro přenos mezi frontendem a backendem

    private String policyNumber; // Číslo pojistky se negeneruje ručně, nastavuje ho backend


    @NotBlank(message = "Typ pojištění nesmí být prázdný")
    private String type; // Typ pojištění (např. životní, cestovní)

    @NotNull(message = "Částka nesmí být prázdná")
    @Min(value = 0, message = "Částka pojištění nesmí být záporná")
    private double amount;

    private LocalDate validFrom;
    private LocalDate validTo;

    private boolean template; // True = šablona, False = konkrétní pojištění

    private List<Long> insuredPersonIds;  // Seznam pojištěných osob podle jejich ID
}
