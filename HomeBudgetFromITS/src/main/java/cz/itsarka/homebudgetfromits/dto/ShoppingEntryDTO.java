package cz.itsarka.homebudgetfromits.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ShoppingEntryDTO {
    private Long id;
    private int amount;
    private LocalDateTime timestamp;
    private int itemNumber;  // Přidáno číslo položky
    private Long expenseId;  // Přidáno pro identifikaci výdaje
}


