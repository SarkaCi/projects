package cz.itsarka.homebudgetfromits.controller;

import cz.itsarka.homebudgetfromits.dto.ExpenseDTO;
import cz.itsarka.homebudgetfromits.mapper.ExpenseMapper;
import cz.itsarka.homebudgetfromits.repository.ExpenseRepository;
import cz.itsarka.homebudgetfromits.repository.ShoppingEntryRepository;
import cz.itsarka.homebudgetfromits.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseWebController {
    private final ExpenseService expenseService;
    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    private final ShoppingEntryRepository shoppingEntryRepository;

    @GetMapping("/{year}/{month}")
    public String getExpensesForMonth(@PathVariable int year, @PathVariable int month, Model model) {
        List<Integer> availableYears = expenseService.getAvailableYears();

        model.addAttribute("expenses", expenseService.getExpensesForMonth(year, month));
        model.addAttribute("availableYears", availableYears);
        return "home_budget";
    }

    @PostMapping("/update")
    public String updateExpenses(@ModelAttribute ExpenseDTO expenseDTO) {
        expenseService.saveExpense(expenseDTO);
        return "redirect:/expenses/list"; // ✅ Po uložení přesměrování na seznam výdajů
    }


    @PostMapping("/addUser")
    public String addUser(@RequestParam String user, @RequestParam int year, @RequestParam int month) {
        try {
            expenseService.createNewUser(user, year, month);
        } catch (IllegalArgumentException e) {
            return "redirect:/expenses?error=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
        }
        return "redirect:/expenses/list"; // Po přidání přesměrování na seznam výdajů
    }


    @GetMapping("/edit/{id}")
    public String editExpense(@PathVariable Long id, Model model) {
        ExpenseDTO expenseDTO = expenseService.getExpenseById(id);
        model.addAttribute("expense", expenseDTO);
        return "edit_expense";
    }


    @PostMapping("/removeUser")
    public String removeUser(@RequestParam(required = false) Long id) {
        if (id == null) {
            System.out.println("❌ Chyba: ID je null při mazání uživatele!");
            return "redirect:/expenses/list?error=invalid_id";
        }

        System.out.println("🗑️ Mažu uživatele s ID: " + id);
        expenseService.deleteUserById(id);

        return "redirect:/expenses/list";
    }


    @GetMapping("")
    public String redirectToHome(Model model) {
        LocalDate today = LocalDate.now();
        List<ExpenseDTO> expenses = expenseService.getExpensesForMonth(today.getYear(), today.getMonthValue());
        model.addAttribute("expenses", expenses);
        return "home_budget";
    }


    @PostMapping("/shopping/add")
    public String addShoppingEntry(@RequestParam Long expenseId,
                                   @RequestParam int amount,
                                   Model model) {
        expenseService.addShoppingEntry(expenseId, amount);
        model.addAttribute("expenseId", expenseId);
        model.addAttribute("shoppingEntries", expenseService.getExpenseById(expenseId).getShoppingEntries());
        return "shopping_edit";  // ✅ Zůstane na stránce a aktualizuje seznam
    }

    @PostMapping("/shopping/remove")
    public String removeShoppingEntry(@RequestParam Long entryId, @RequestParam Long expenseId, Model model) {
        expenseService.removeShoppingEntry(entryId);
        model.addAttribute("expenseId", expenseId);
        model.addAttribute("shoppingEntries", expenseService.getExpenseById(expenseId).getShoppingEntries());
        return "shopping_edit";  // ✅ Zůstává na stránce
    }

    //Přidání metody pro zobrazení seznamu všech výdajů.
    @GetMapping("/list")
    public String listAllExpenses(Model model) {
        List<ExpenseDTO> expenses = expenseService.getAllExpenses();
        model.addAttribute("expenses", expenses); // ZDE SE NAČÍTÁ SEZNAM
        return "expense_list";
    }

    //Otevírá shopping_edit.html s aktuálními daty.
    @GetMapping("/shopping/edit/{id}")
    public String editShoppingEntries(@PathVariable Long id, Model model) {
        ExpenseDTO expenseDTO = expenseService.getExpenseById(id);
        model.addAttribute("expenseId", expenseDTO.getId());
        model.addAttribute("shoppingEntries", expenseDTO.getShoppingEntries());
        return "shopping_edit";
    }

    @PostMapping("/expected_contribution/add")
    public String addExpectedContribution(@RequestParam Long expenseId, @RequestParam int amount, Model model) {
        expenseService.addExpectedContribution(expenseId, amount);
        model.addAttribute("expenseId", expenseId);
        model.addAttribute("expectedContributions", expenseService.getExpenseById(expenseId).getExpectedContributions());
        return "expected_contribution_edit";
    }


    @GetMapping("/expected_contribution/edit/{id}")
    public String editExpectedContributions(@PathVariable Long id, Model model) {
        ExpenseDTO expenseDTO = expenseService.getExpenseById(id);
        model.addAttribute("expenseId", expenseDTO.getId());
        model.addAttribute("expectedContributions", expenseDTO.getExpectedContributions());
        return "expected_contribution_edit";
    }


    @PostMapping("/expected_contribution/remove")
    public String removeExpectedContribution(@RequestParam Long entryId, @RequestParam Long expenseId, Model model) {
        expenseService.removeExpectedContribution(entryId);
        model.addAttribute("expenseId", expenseId);
        model.addAttribute("expectedContributions", expenseService.getExpenseById(expenseId).getExpectedContributions());
        return "expected_contribution_edit";
    }


    @GetMapping("/filter")
    public String filterExpenses(@RequestParam int year, @RequestParam int month, Model model) {
        List<ExpenseDTO> expenses = expenseService.getExpensesForMonth(year, month);
        model.addAttribute("expenses", expenses);
        model.addAttribute("year", year);  // Přidání do modelu
        model.addAttribute("month", month); // Přidání do modelu
        return "home_budget"; // Vrací stejný template s novými daty
    }

    // Metoda pro zobrazení výdajů mezi dvěma daty (roky a měsíce)
    @GetMapping("/between-dates")
    public String getExpensesBetweenDates(
            @RequestParam("startYear") int startYear,
            @RequestParam("startMonth") int startMonth,
            @RequestParam("endYear") int endYear,
            @RequestParam("endMonth") int endMonth,
            @RequestParam(value = "user", required = false) String user,
            @RequestParam(value = "categories", required = false) List<String> categories,
            Model model) {

        List<ExpenseDTO> expenses = expenseService.getExpensesBetweenDates(startYear, startMonth, endYear, endMonth, user, categories);
        model.addAttribute("expenses", expenses);
        return "home_budget";
    }

    

}
