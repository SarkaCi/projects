package cz.itsarka.homebudgetfromits.repository;

import cz.itsarka.homebudgetfromits.entity.ShoppingEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ShoppingEntryRepository extends JpaRepository<ShoppingEntry, Long> {
    List<ShoppingEntry> findByExpenseId(Long expenseId);
}

