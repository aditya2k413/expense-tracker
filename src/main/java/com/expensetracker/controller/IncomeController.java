package com.expensetracker.controller;

import com.expensetracker.model.Income;
import com.expensetracker.service.IncomeService;
import com.expensetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/income")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Income>> getAllIncome() {
        List<Income> incomes = incomeService.getAll(userService.findById(1L));
        if (incomes.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(incomes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveIncome(@RequestBody Income income) {
        if (income.getAmount() != null) return new ResponseEntity<>(incomeService.add(income), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Income> updateIncome(@PathVariable Long id, @RequestBody Income income) {
        Income updated = incomeService.update(income);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIncomeById(@PathVariable Long id) {
        incomeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/by-month")
    public ResponseEntity<List<Income>> getIncomeByMonth(@RequestParam int month, @RequestParam int year) {
        List<Income> incomes = incomeService.getIncomesByMonth(userService.findById(1L), month, year);
        if (incomes.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(incomes, HttpStatus.OK);
    }
}
