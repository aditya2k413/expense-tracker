package com.expensetracker.service;

import com.expensetracker.model.Expense;
import com.expensetracker.model.User;
import com.expensetracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> getAllExpenses(User user) {
        return expenseRepository.findByUser(user);
    }

    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public Expense update(Expense expense) {
        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public Map<String, BigDecimal> getExpensesByCategoryFiltered(User user, Integer month, Integer year) {
        List<Expense> expenses = (month != null && year != null) ? getExpensesByMonth(user, month, year) : expenseRepository.findByUser(user);
        Map<String, BigDecimal> result = new HashMap<>();
        for (Expense expense : expenses) {
            String categoryName = expense.getCategory() != null ? expense.getCategory().getName() : "Uncategorized";
            result.merge(categoryName, expense.getAmount(), BigDecimal::add);
        }
        return result;
    }

    public List<Expense> getExpensesByMonth(User user, int month, int year) {
        List<Expense> allExpenses = expenseRepository.findByUser(user);
        List<Expense> filtered = new ArrayList<>();
        for (Expense expense : allExpenses) {
            if (expense.getDate().getMonthValue() == month && expense.getDate().getYear() == year) {
                filtered.add(expense);
            }
        }
        return filtered;
    }

    public Map<String, BigDecimal> getExpensesByCategory(User user) {
        return getExpensesByCategoryFiltered(user, null, null);
    }
}
