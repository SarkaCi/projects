package cz.itsarka.homebudgetfromits.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ExpectedContributionEntryDTO {
    private Long id;
    private int amount;
    private LocalDateTime timestamp;
    private int itemNumber;
    private Long expenseId;
}


