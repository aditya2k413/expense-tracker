package com.expensetracker.controller;

import com.expensetracker.model.Budget;
import com.expensetracker.model.Income;
import com.expensetracker.model.SavingsGoal;
import com.expensetracker.model.User;
import com.expensetracker.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ai")
public class AiController {

    @Autowired private AiService aiService;
    @Autowired private ExpenseService expenseService;
    @Autowired private IncomeService incomeService;
    @Autowired private BudgetService budgetService;
    @Autowired private SavingsGoalService savingsGoalService;
    @Autowired private UserService userService;

    @GetMapping("/summary")
    public ResponseEntity<String> getMonthlySummary(
            @RequestParam int month, @RequestParam int year) {
        User user = userService.findById(1L);
        Map<String, BigDecimal> expensesByCategory = expenseService.getExpensesByCategory(user);
        BigDecimal totalExpenses = expensesByCategory.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        List<Income> incomes = incomeService.getAll(user);
        BigDecimal totalIncome = incomes.stream()
                .map(Income::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        String result = aiService.getMonthlySpendingSummary(
                expensesByCategory, totalExpenses, totalIncome, month, year);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/budget-suggestions")
    public ResponseEntity<String> getBudgetSuggestions() {
        User user = userService.findById(1L);
        Map<String, BigDecimal> expensesByCategory = expenseService.getExpensesByCategory(user);
        List<Budget> budgets = budgetService.getAll(user);
        String result = aiService.getBudgetSuggestions(expensesByCategory, budgets);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/savings-advice")
    public ResponseEntity<String> getSavingsAdvice() {
        User user = userService.findById(1L);
        List<SavingsGoal> savingsGoals = savingsGoalService.getAll(user);
        List<Income> incomes = incomeService.getAll(user);
        BigDecimal totalIncome = incomes.stream()
                .map(Income::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Map<String, BigDecimal> expensesByCategory = expenseService.getExpensesByCategory(user);
        BigDecimal totalExpenses = expensesByCategory.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        String result = aiService.getSavingsAdvice(savingsGoals, totalIncome, totalExpenses);
        return ResponseEntity.ok(result);
    }
}
