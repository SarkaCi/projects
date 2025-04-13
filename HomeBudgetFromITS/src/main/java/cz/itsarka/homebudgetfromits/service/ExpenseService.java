package cz.itsarka.homebudgetfromits.service;


import cz.itsarka.homebudgetfromits.dto.ExpectedContributionEntryDTO;
import cz.itsarka.homebudgetfromits.dto.ExpenseDTO;
import cz.itsarka.homebudgetfromits.dto.ShoppingEntryDTO;
import cz.itsarka.homebudgetfromits.entity.ExpectedContributionEntry;
import cz.itsarka.homebudgetfromits.entity.Expense;
import cz.itsarka.homebudgetfromits.entity.ShoppingEntry;
import cz.itsarka.homebudgetfromits.mapper.ExpectedContributionMapper;
import cz.itsarka.homebudgetfromits.mapper.ExpenseMapper;
import cz.itsarka.homebudgetfromits.repository.ExpectedContributionRepository;
import cz.itsarka.homebudgetfromits.repository.ExpenseRepository;
import cz.itsarka.homebudgetfromits.repository.ShoppingEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ShoppingEntryRepository shoppingEntryRepository;
    private final ExpectedContributionRepository expectedContributionRepository;
    private final ExpenseMapper expenseMapper;
    private final ExpectedContributionMapper expectedContributionMapper;

    /**
     * Získá všechny výdaje pro daný měsíc a rok.
     */
    public List<ExpenseDTO> getExpensesForMonth(int year, int month) {
        return expenseRepository.findByYearAndMonth(year, month)
                .stream()
                .map(expense -> {
                    ExpenseDTO dto = expenseMapper.toDto(expense);
                    dto.updateBalanceStatus(); // 🔄 Přepočítání balance statusu
                    return dto;
                })
                .collect(Collectors.toList());
    }



    /**
     * Získá seznam dostupných let pro uživatele.
     */
    public List<Integer> getAvailableYears() {
        return expenseRepository.findAll()
                .stream()
                .map(Expense::getYear)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Uloží nebo aktualizuje výdaje uživatele.
     */
    @Transactional
    public ExpenseDTO saveExpense(ExpenseDTO expenseDTO) {
        Expense expense = expenseRepository.findById(expenseDTO.getId())
                .orElse(new Expense());

        expense.setUser(expenseDTO.getUser());
        expense.setYear(expenseDTO.getYear());
        expense.setMonth(expenseDTO.getMonth());
        expense.setElectricity(expenseDTO.getElectricity());
        expense.setGas(expenseDTO.getGas());
        expense.setRental(expenseDTO.getRental());
        expense.setCar(expenseDTO.getCar());
        expense.setPetrol(expenseDTO.getPetrol());
        expense.setInternet(expenseDTO.getInternet());
        expense.setLunch(expenseDTO.getLunch());

        expenseRepository.save(expense);
        ExpenseDTO updatedDTO = expenseMapper.toDto(expense);
        updatedDTO.updateBalanceStatus(); // ✅ Aktualizace balance statusu
        return updatedDTO;
    }



    /**
     * Přidá nového uživatele s výchozími výdaji.
     */
    @Transactional
    public void createNewUser(String user, int year, int month) {
        if (expenseRepository.findByUserAndYearAndMonth(user, year, month).isPresent()) {
            throw new IllegalArgumentException("Uživatel již existuje.");
        }

        Expense expense = new Expense();
        expense.setUser(user);
        expense.setYear(year);
        expense.setMonth(month);
        expenseRepository.save(expense);
    }

    /**
     * Odstraní uživatele a jeho výdaje.
     */
    @Transactional
    public void deleteUser(String user, int year, int month) {
        expenseRepository.findByUserAndYearAndMonth(user, year, month)
                .ifPresent(expenseRepository::delete);
    }

    @Transactional
    public void deleteUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("❌ Chyba: `id` nesmí být null!");
        }

        Expense expense = expenseRepository.findById(id)
                .orElse(null);

        if (expense == null) {
            System.out.println("❌ Výdaj s ID " + id + " nenalezen!");
            return; // ❗ Přidáno, aby aplikace nespadla
        }

        // ✅ Logujeme, co odstraňujeme
        System.out.println("🗑️ Mažu výdaj ID: " + id);

        // ✅ Odstraníme související záznamy
        shoppingEntryRepository.deleteAll(expense.getShoppingEntries());
        expectedContributionRepository.deleteAll(expense.getExpectedContributions());

        // ✅ Odstraníme samotný výdaj
        expenseRepository.delete(expense);
    }



    /**
     * Přidá položku do `shoppingInStore`.
     */
    @Transactional
    public ShoppingEntryDTO addShoppingEntry(Long expenseId, int amount) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new IllegalArgumentException("Výdaje nenalezeny"));

        // Inicializace seznamu, pokud je null
        if (expense.getShoppingEntries() == null) {
            expense.setShoppingEntries(new ArrayList<>());
        }

        // Určíme číslo nové položky (1, 2, 3...)
        int newItemNumber = expense.getShoppingEntries().size() + 1;

        ShoppingEntry entry = new ShoppingEntry();
        entry.setAmount(amount);
        entry.setTimestamp(LocalDateTime.now());
        entry.setExpense(expense);
        entry.setItemNumber(newItemNumber); // Automatické číslování položek

        shoppingEntryRepository.save(entry);
        expense.getShoppingEntries().add(entry);

        int totalShopping = expense.getShoppingEntries().stream()
                .mapToInt(ShoppingEntry::getAmount)
                .sum();
        expense.setShoppingInStore(totalShopping);
        expenseRepository.save(expense);

        ShoppingEntryDTO shoppingEntryDTO = new ShoppingEntryDTO();
        shoppingEntryDTO.setId(entry.getId());
        shoppingEntryDTO.setAmount(entry.getAmount());
        shoppingEntryDTO.setTimestamp(entry.getTimestamp());
        shoppingEntryDTO.setItemNumber(entry.getItemNumber());
        shoppingEntryDTO.setExpenseId(expense.getId());

        return shoppingEntryDTO;
    }





    /**
     * Odebere položku ze `shoppingInStore`.
     */
    @Transactional
    public void removeShoppingEntry(Long entryId) {
        ShoppingEntry entry = shoppingEntryRepository.findById(entryId)
                .orElseThrow(() -> new IllegalArgumentException("Položka nenalezena"));

        Expense expense = entry.getExpense();
        shoppingEntryRepository.delete(entry);
        expense.getShoppingEntries().remove(entry);

        int totalShopping = expense.getShoppingEntries().stream()
                .mapToInt(ShoppingEntry::getAmount)
                .sum();
        expense.setShoppingInStore(totalShopping);
        expenseRepository.save(expense);
    }





    //Přidání metody pro načtení výdajů podle ID.
    public ExpenseDTO getExpenseById(Long id) {
        ExpenseDTO expenseDTO = expenseRepository.findById(id)
                .map(expenseMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Výdaj nenalezen"));

        expenseDTO.updateBalanceStatus(); // ✅ Přepočet stavu
        return expenseDTO;
    }


    //Přidání metody pro načtení všech výdajů.
    public List<ExpenseDTO> getAllExpenses() {
        return expenseRepository.findAllByOrderByIdDesc() // 🔥 Výdaje seřazené od nejnovějších
                .stream()
                .map(expense -> {
                    ExpenseDTO dto = expenseMapper.toDto(expense);
                    dto.updateBalanceStatus();
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public ExpectedContributionEntryDTO addExpectedContribution(Long expenseId, int amount) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new IllegalArgumentException("Výdaj nenalezen"));

        int newItemNumber = expectedContributionRepository.findByExpenseId(expenseId).size() + 1;

        ExpectedContributionEntry entry = new ExpectedContributionEntry();
        entry.setAmount(amount);
        entry.setTimestamp(LocalDateTime.now());
        entry.setExpense(expense);
        entry.setItemNumber(newItemNumber);

        expectedContributionRepository.save(entry);

        // ✅ Po přidání příspěvku aktualizujeme balanceStatus
        ExpenseDTO expenseDTO = expenseMapper.toDto(expense);
        expenseDTO.updateBalanceStatus();

        return expectedContributionMapper.toDto(entry);
    }


    @Transactional
    public void removeExpectedContribution(Long entryId) {
        ExpectedContributionEntry entry = expectedContributionRepository.findById(entryId)
                .orElseThrow(() -> new IllegalArgumentException("Položka nenalezena"));

        Expense expense = entry.getExpense();
        expectedContributionRepository.delete(entry);
        expense.getExpectedContributions().remove(entry); // ✅ Odebrání z výdajů

        int totalContributions = expense.getExpectedContributions().stream()
                .mapToInt(ExpectedContributionEntry::getAmount)
                .sum();
        expense.setExpectedContributionSum(totalContributions); // ✅ Přepočet fondu
        expenseRepository.save(expense); // ✅ Uložení změn
    }


    /**
     * Získá seznam výdajů mezi dvěma daty.
     */
    public List<ExpenseDTO> getExpensesBetweenDates(int startYear, int startMonth, int endYear, int endMonth, String user, List<String> categories) {
        List<Expense> expenses = expenseRepository.findByYearAndMonthBetween(startYear, startMonth, endYear, endMonth);

        // Filtrování podle uživatele
        if (user != null && !user.equalsIgnoreCase("all")) {
            expenses = expenses.stream()
                    .filter(expense -> expense.getUser().equalsIgnoreCase(user))
                    .collect(Collectors.toList());
        }

        List<ExpenseDTO> expenseDTOs = expenseMapper.toDtoList(expenses);

        // Pokud nejsou vybrané kategorie, vrátíme všechny údaje
        if (categories == null || categories.isEmpty()) {
            return expenseDTOs;
        }

        // Filtrování dat podle vybraných kategorií
        for (ExpenseDTO expense : expenseDTOs) {
            expense.filterCategories(categories);
        }

        return expenseDTOs;
    }

    public List<String> getAllUsers() {
        return expenseRepository.findAll()
                .stream()
                .map(Expense::getUser)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }


}
