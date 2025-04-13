import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.Period;

// Třída reprezentující pojištěnce
class InsuredPerson {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String phoneNumber;

    /*BEZPARAMETRICKÝ KONSTRUKTOR PRO JACKSON
    Je nutný pro deserializaci JSON do objektu InsuredPerson.
    Jackson ho vyžaduje, aby mohl vytvořit objekt a následně do něj doplnit hodnoty.*/
    public InsuredPerson() {}

    //KONSTRUKTOR S ANOTACEMI PRO JACKSON
    @JsonCreator
    public InsuredPerson(
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("birthDate") LocalDate birthDate,
            @JsonProperty("phoneNumber") String phoneNumber) {
        // Validace telefonního čísla (musí mít správný formát)
        if (!phoneNumber.matches("\\+?[0-9]{9,15}")) {
            throw new IllegalArgumentException("❌ Neplatné telefonní číslo.");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
    }

    // Jackson bude ignorovat pole "age" při serializaci i deserializaci
    @JsonIgnore
    public int getAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    //Settery
    // Nastavení telefonního čísla s validací
    public void setPhoneNumber(String phoneNumber) {
        if (!phoneNumber.matches("\\+?[0-9]{9,15}")) {
            throw new IllegalArgumentException("❌ Neplatné telefonní číslo.");
        }
        this.phoneNumber = phoneNumber;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    //Gettery
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    /* Přepsaná metoda toString()
    Vypisuje informace o pojištěnci ve formátu Jméno Příjmení, narozen: YYYY-MM-DD, věk: X let, tel.: číslo.
    Používá se například při zobrazování seznamu pojištěnců.*/
    @Override
    public String toString() {
        return firstName + " " + lastName + ", narozen: " + birthDate + ", věk: " + getAge() + ", tel.: " + phoneNumber;
    }
}
