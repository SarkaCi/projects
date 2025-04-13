package cz.itsarka.homebudgetfromits.controller;


import cz.itsarka.homebudgetfromits.dto.ExpectedContributionEntryDTO;
import cz.itsarka.homebudgetfromits.dto.ExpenseDTO;
import cz.itsarka.homebudgetfromits.entity.Expense;
import cz.itsarka.homebudgetfromits.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "*")  // 🔥 Povolení CORS pro všechny domény
@RequiredArgsConstructor
public class ExpenseRestController {

    private final ExpenseService expenseService;

    @GetMapping("/{year}/{month}")
    public List<ExpenseDTO> getExpensesForMonth(@PathVariable int year, @PathVariable int month) {
        return expenseService.getExpensesForMonth(year, month);
    }

    @PostMapping("/update")
    public ExpenseDTO updateExpenses(@RequestBody ExpenseDTO expenseDTO) {
        return expenseService.saveExpense(expenseDTO);
    }

    @PostMapping("/addUser")
    public void addUser(@RequestParam String user, @RequestParam int year, @RequestParam int month) {
        expenseService.createNewUser(user, year, month);
    }

    @PostMapping("/removeUser")
    public void removeUser(@RequestParam String user, @RequestParam int year, @RequestParam int month) {
        expenseService.deleteUser(user, year, month);
    }

    @PostMapping("/shopping/add")
    public void addShoppingEntry(@RequestParam Long expenseId, @RequestParam int amount) {
        expenseService.addShoppingEntry(expenseId, amount);
    }

    @PostMapping("/shopping/remove")
    public void removeShoppingEntry(@RequestParam Long entryId) {
        expenseService.removeShoppingEntry(entryId);
    }

    @PostMapping("/add")
    public ExpectedContributionEntryDTO addExpectedContribution(@RequestParam Long expenseId, @RequestParam int amount) {
        return expenseService.addExpectedContribution(expenseId, amount);
    }

    @PostMapping("/remove")
    public void removeExpectedContribution(@RequestParam Long entryId) {
        expenseService.removeExpectedContribution(entryId);
    }

    // RestController pro získání výdajů mezi dvěma daty
    @GetMapping("/between-dates")
    public List<ExpenseDTO> getExpensesBetweenDates(
            @RequestParam("startYear") int startYear,
            @RequestParam("startMonth") int startMonth,
            @RequestParam("endYear") int endYear,
            @RequestParam("endMonth") int endMonth,
            @RequestParam(value = "user", required = false) String user,
            @RequestParam(value = "categories", required = false) List<String> categories) {

        return expenseService.getExpensesBetweenDates(startYear, startMonth, endYear, endMonth, user, categories);
    }

    @GetMapping("/all-users")
    public List<String> getAllUsers() {
        return expenseService.getAllUsers();
    }




}
