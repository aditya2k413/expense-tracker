package com.expensetracker.service;

import com.expensetracker.model.Budget;
import com.expensetracker.model.User;
import com.expensetracker.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {
    @Autowired
    private BudgetRepository budgetRepository;

    public List<Budget> getAll(User user) {
        return budgetRepository.findByUser(user);
    }

    public Budget add(Budget budget) {
        return budgetRepository.save(budget);
    }

    public Budget update(Budget budget) {
        return budgetRepository.save(budget);
    }

    public void delete(Long id) {
        budgetRepository.deleteById(id);
    }

    public List<Budget> getByMonthAndYear(User user, Integer month, Integer year) {
        return budgetRepository.findByUserAndMonthAndYear(user, month, year);
    }
}
