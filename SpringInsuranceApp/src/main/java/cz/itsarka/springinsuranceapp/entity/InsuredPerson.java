package cz.itsarka.springinsuranceapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

/**
 * Entita reprezentující pojištěnou osobu. Obsahuje základní informace jako jméno,
 * příjmení, email, telefon, datum narození a adresu.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "insured_persons")
public class InsuredPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primární klíč, automaticky generovaný

    @NotBlank(message = "Jméno nesmí být prázdné")
    private String firstName; // Jméno pojištěné osoby

    @NotBlank(message = "Příjmení nesmí být prázdné")
    private String lastName; // Příjmení pojištěné osoby

    @Email(message = "Neplatný email")
    private String email; // Emailová adresa pojištěné osoby

    @NotBlank(message = "Telefonní číslo nesmí být prázdné")
    @Pattern(
            regexp = "^(\\+420\\s?)?\\d{3}\\s?\\d{3}\\s?\\d{3}$",
            message = "Telefonní číslo musí být ve formátu +420 777 123 456 nebo 777 123 456"
    )
    private String phone; // Telefonní číslo pojištěné osoby

    @NotNull(message = "Datum narození nesmí být prázdné")
    @Column(name = "date_of_birth", columnDefinition = "DATE") // Použij přímo DATE v databázi
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) // yyyy-MM-dd (což je standard pro <input type="date">)
    private LocalDate dateOfBirth; // Datum narození pojištěné osoby


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address; // Adresa pojištěné osoby

    @ManyToMany
    @JoinTable(
            name = "insured_person_insurance",
            joinColumns = @JoinColumn(name = "insured_person_id"),
            inverseJoinColumns = @JoinColumn(name = "insurance_id")
    )
    private List<Insurance> insurances; // Seznam pojištění, která má daná osoba uzavřená
}

