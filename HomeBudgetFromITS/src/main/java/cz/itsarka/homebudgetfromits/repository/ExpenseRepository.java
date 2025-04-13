package cz.itsarka.homebudgetfromits.repository;

import cz.itsarka.homebudgetfromits.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    Optional<Expense> findByUserAndYearAndMonth(String user, int year, int month);
    List<Expense> findByUserAndYear(String user, int year);
    List<Expense> findByYearAndMonth(int year, int month);


    // 🔥 Přidáno: Seřazení výdajů podle ID sestupně (nejnovější nahoře)
    List<Expense> findAllByOrderByIdDesc();

    // Metoda pro hledání výdajů mezi dvěma daty
    @Query("SELECT e FROM Expense e WHERE (e.year > :startYear OR (e.year = :startYear AND e.month >= :startMonth))" +
            " AND (e.year < :endYear OR (e.year = :endYear AND e.month <= :endMonth))")
    List<Expense> findByYearAndMonthBetween(@Param("startYear") int startYear,
                                            @Param("startMonth") int startMonth,
                                            @Param("endYear") int endYear,
                                            @Param("endMonth") int endMonth);
}




