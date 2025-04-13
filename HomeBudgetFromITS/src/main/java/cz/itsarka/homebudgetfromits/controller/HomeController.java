package cz.itsarka.homebudgetfromits.controller;

import cz.itsarka.homebudgetfromits.dto.ExpenseDTO;
import cz.itsarka.homebudgetfromits.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ExpenseService expenseService;

    @GetMapping("/")
    public String home(Model model) {
        LocalDate today = LocalDate.now();
        List<ExpenseDTO> expenses = expenseService.getExpensesForMonth(today.getYear(), today.getMonthValue());
        model.addAttribute("expenses", expenses);
        return "home_budget";
    }
}
