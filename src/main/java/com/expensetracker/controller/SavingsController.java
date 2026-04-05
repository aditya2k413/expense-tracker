package com.expensetracker.controller;

import com.expensetracker.model.SavingsGoal;
import com.expensetracker.service.SavingsGoalService;
import com.expensetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/savings")
public class SavingsController {

    @Autowired
    private SavingsGoalService savingsGoalService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<SavingsGoal>> getAllSavings() {
        List<SavingsGoal> savings = savingsGoalService.getAll(userService.findById(1L));
        if (savings.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(savings, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveSavingsGoal(@RequestBody SavingsGoal savingsGoal) {
        if (savingsGoal.getTargetAmount() != null) return new ResponseEntity<>(savingsGoalService.add(savingsGoal), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SavingsGoal> updateSavingsGoal(@PathVariable Long id, @RequestBody SavingsGoal savingsGoal) {
        SavingsGoal updated = savingsGoalService.update(savingsGoal);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSavingsGoalById(@PathVariable Long id) {
        savingsGoalService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
