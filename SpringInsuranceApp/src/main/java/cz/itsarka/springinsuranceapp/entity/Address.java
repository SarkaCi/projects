package cz.itsarka.springinsuranceapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

/**
 * Entita reprezentující adresu. Obsahuje základní informace jako ulici, číslo popisné,
 * město, PSČ a stát. Používá JPA anotace pro mapování na databázovou tabulku "addresses".
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primární klíč, automaticky generovaný

    @NotBlank(message = "Ulice nesmí být prázdná")
    private String street; // Název ulice

    @Pattern(
            regexp = "\\d+[A-Za-z]?(/\\d+[A-Za-z]?)?",
            message = "Číslo popisné/místní musí být ve formátu 123/45, 123a/45b, 123A nebo 123"
    )
    private String streetNumber; // Číslo popisné/místní

    @NotBlank(message = "Město nesmí být prázdné")
    private String city; // Název města

    @Pattern(
            regexp = "\\d{3}\\s?\\d{2}",
            message = "PSČ musí obsahovat 5 číslic (např. 12345 nebo 123 45)"
    )
    private String postalCode; // Poštovní směrovací číslo

    @NotBlank(message = "Stát nesmí být prázdný")
    private String country; // Stát
}
