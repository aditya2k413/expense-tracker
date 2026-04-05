package com.expensetracker.controller;

import com.expensetracker.model.Budget;
import com.expensetracker.service.BudgetService;
import com.expensetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/budgets")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Budget>> getAllBudgets() {
        List<Budget> budgets = budgetService.getAll(userService.findById(1L));
        if (budgets.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(budgets, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveBudget(@RequestBody Budget budget) {
        if (budget.getLimitAmount() != null) return new ResponseEntity<>(budgetService.add(budget), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Budget> updateBudget(@PathVariable Long id, @RequestBody Budget budget) {
        Budget updated = budgetService.update(budget);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBudgetById(@PathVariable Long id) {
        budgetService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
