package cz.itsarka.springinsuranceapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Data Transfer Object (DTO) pro přenos informací o pojištěné osobě mezi frontendem a backendem.
 * DTO zajišťuje validaci vstupních dat a slouží k ochraně vnitřní struktury aplikace.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsuredPersonDTO {

    private Long id; // ID pojištěné osoby, používá se pro přenos mezi frontendem a backendem


    @NotBlank(message = "Jméno nesmí být prázdné")
    private String firstName;

    @NotBlank(message = "Příjmení nesmí být prázdné")
    private String lastName;

    @Email(message = "Neplatný email")
    private String email;

    @NotBlank(message = "Telefonní číslo nesmí být prázdné")
    @Pattern(
            regexp = "^(\\+420\\s?)?\\d{3}\\s?\\d{3}\\s?\\d{3}$",
            message = "Telefonní číslo musí být ve formátu +420 777 123 456 nebo 777 123 456"
    )
    private String phone;

    @NotNull(message = "Datum narození nesmí být prázdné")
    private LocalDate dateOfBirth;

    @NotNull(message = "Adresa nesmí být prázdná")
    private AddressDTO address;

    private List<Long> insuranceIds;  // Seznam pojištění (ID jednotlivých pojištění)
}
