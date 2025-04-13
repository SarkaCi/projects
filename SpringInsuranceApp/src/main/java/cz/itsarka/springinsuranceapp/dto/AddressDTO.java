package cz.itsarka.springinsuranceapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;


/**
 * Data Transfer Object (DTO) pro přenos informací o adrese mezi frontendem a backendem.
 * DTO slouží k ochraně vnitřní struktury aplikace a usnadnění validace dat.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private Long id;  // ID adresy, zachováno pro přenos mezi frontendem a backendem


    @NotBlank(message = "Ulice nesmí být prázdná")
    private String street;

    @Pattern(
            regexp = "\\d+[A-Za-z]?(/\\d+[A-Za-z]?)?",
            message = "Číslo popisné/místní musí být ve formátu 123/45, 123a/45b, 123A nebo 123"
    )
    private String streetNumber;

    @NotBlank(message = "Město nesmí být prázdné")
    private String city;

    @Pattern(
            regexp = "\\d{3}\\s?\\d{2}",
            message = "PSČ musí obsahovat 5 číslic (např. 12345 nebo 123 45)"
    )
    private String postalCode;

    @NotBlank(message = "Stát nesmí být prázdný")
    private String country;
}
