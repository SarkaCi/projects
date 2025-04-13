package cz.itsarka.homebudgetfromits.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "expected_contributions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpectedContributionEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int amount; // Hodnota příspěvku
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private int itemNumber; // Číslování položek

    @ManyToOne
    @JoinColumn(name = "expense_id", nullable = false)
    @JsonBackReference
    private Expense expense;
}

