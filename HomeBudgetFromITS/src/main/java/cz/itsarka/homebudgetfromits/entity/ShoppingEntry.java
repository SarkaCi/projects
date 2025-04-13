package cz.itsarka.homebudgetfromits.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "shopping_entries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int amount;
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private int itemNumber; // Přidáno číslo položky

    @ManyToOne
    @JoinColumn(name = "expense_id", nullable = false)
    @JsonBackReference
    private Expense expense;
}


