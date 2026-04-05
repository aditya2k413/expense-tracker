package com.expensetracker.controller;

import com.expensetracker.model.Expense;
import com.expensetracker.model.User;
import com.expensetracker.service.ExpenseService;
import com.expensetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpense( ){
        List<Expense> expenses = expenseService.getAllExpenses(userService.findById(1L));
        if (expenses.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @GetMapping("/by-category")
    public ResponseEntity<Map<String, BigDecimal>> getExpensesByCategory(@RequestParam(required=false) Integer month, @RequestParam(required=false) Integer year) {
        Map<String, BigDecimal> expensesByCategory = expenseService.getExpensesByCategoryFiltered(userService.findById(1L), month, year);
        return new ResponseEntity<>(expensesByCategory, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveExpense(@RequestBody Expense expense){
        if (expense.getAmount()!=null&&expense.getCategory()!=null&&!expense.getAmount().equals("")&&!expense.getCategory().equals("")) return new ResponseEntity<>(expenseService.addExpense(expense), HttpStatus.OK);
        return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense expense) {
        Expense updated = expenseService.update(expense);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<?> deleteExpenseById(@PathVariable Long id){
        expenseService.deleteExpense(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/by-month")
    public ResponseEntity<List<Expense>> getExpensesByMonth(@RequestParam int month, @RequestParam int year) {
        List<Expense> expenses = expenseService.getExpensesByMonth(userService.findById(1L), month, year);
        if (expenses.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }
}
