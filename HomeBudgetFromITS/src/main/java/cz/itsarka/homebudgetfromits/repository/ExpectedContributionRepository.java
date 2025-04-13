package cz.itsarka.homebudgetfromits.repository;

import cz.itsarka.homebudgetfromits.entity.ExpectedContributionEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExpectedContributionRepository extends JpaRepository<ExpectedContributionEntry, Long> {
    List<ExpectedContributionEntry> findByExpenseId(Long expenseId);
}

