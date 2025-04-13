package cz.itsarka.homebudgetfromits.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

// Umožňuje ukládání výdajů pro více uživatelů měsíčně.
@Entity
@Table(name = "expenses")
@Data
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String user;
    private int year;
    private int month;

    private int electricity;
    private int gas;
    private int rental;
    private int car;
    private int petrol;
    private int internet;
    private int lunch;
    private int shoppingInStore;
    private int expectedContributionSum;


    // Vyloučíme seznamy z toString(), aby se zabránilo rekurzivnímu volání
    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonManagedReference
    private List<ShoppingEntry> shoppingEntries = new ArrayList<>();

    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonManagedReference
    private List<ExpectedContributionEntry> expectedContributions = new ArrayList<>();
}

/*
@Entity – označuje tuto třídu jako entitu v JPA, což znamená, že bude uložena v databázi jako tabulka.
@Table(name = "expenses") – specifikuje název tabulky v databázi (expenses).
@Data (z knihovny Lombok) – generuje základní metody jako gettery, settery, toString(), equals(), hashCode().
@Id – označuje primární klíč entity.
@GeneratedValue(strategy = GenerationType.IDENTITY) – nastavuje automatické generování ID pomocí identity sloupce (v SQL AUTO_INCREMENT).
@OneToMany(mappedBy = "expense", cascade = CascadeType.ALL, orphanRemoval = true):
-Vazba "jeden výdaj - mnoho nákupů" (Expense může obsahovat mnoho ShoppingEntry).
-mappedBy = "expense" – v entitě ShoppingEntry existuje pole expense, které vytváří tuto vazbu.
-cascade = CascadeType.ALL – všechny operace (persist, merge, remove) se automaticky propagují do souvisejících ShoppingEntry.
-orphanRemoval = true – pokud je z kolekce odstraněna položka, smaže se i v databázi.
@ToString.Exclude – zamezí výpisu tohoto seznamu v toString() (zabrání rekurzivnímu volání).
@JsonManagedReference – řeší cyklické reference v JSON (pomáhá serializaci do API).
🔹 Co to znamená?
Každý Expense může mít několik ShoppingEntry, kde každá nákupní položka odkazuje zpět na svůj Expense.


        Třída Expense reprezentuje měsíční výdaje jednoho uživatele.
        Obsahuje základní atributy: uživatel, rok, měsíc a kategorie výdajů.
        Má dvě kolekce:
        shoppingEntries – seznam nákupních položek spojených s tímto výdajem.
        expectedContributions – seznam očekávaných příspěvků pro tento výdaj.
        Vazby @OneToMany umožňují databázové propojení mezi Expense a dalšími entitami (ShoppingEntry a ExpectedContributionEntry).
        Lombok (@Data) generuje potřebné metody.
        Použití JPA anotací (@Entity, @Table, @OneToMany, @Id, atd.) umožňuje automatické mapování na databázovou tabulku.
*/
