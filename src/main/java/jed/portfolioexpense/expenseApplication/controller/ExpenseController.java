package jed.portfolioexpense.expenseApplication.controller;

import jed.portfolioexpense.expenseApplication.model.Expense;
import jed.portfolioexpense.expenseApplication.service.ExpenseService;
import jed.portfolioexpense.expenseApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expenses/")
public class ExpenseController {

    @Autowired
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService, UserService userService) {
        this.expenseService = expenseService;
    }

    // Adding expenses
    @PostMapping("{userId}")
    public ResponseEntity<?> addExpense(@PathVariable String userId, @RequestBody Expense expense) {
        try {
            // Add expense
            expenseService.addExpense(userId, expense);

            // Return success message
            return new ResponseEntity<>("Expense added successfully.", HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            // User cannot be found
            return new ResponseEntity<>("User not found. Ensure User ID is correct.", HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            // Invalid expense details
            return new ResponseEntity<>("Invalid expense. Ensure amount is greater than 0 and description is not empty.", HttpStatus.BAD_REQUEST);
        }
    }


    // Getting list of expenses
    @GetMapping("{userId}")
    public ResponseEntity<?> getExpenses(@PathVariable String userID) {
        try {
            // Get user expenses
            List<Expense> userExpenses = expenseService.getUserExpensesById(userID);

            // Return user expenses on succession
            return new ResponseEntity<>(userExpenses, HttpStatus.OK);
        } catch (IllegalStateException e) {
            // User cannot be found
            return new ResponseEntity<>("No expenses found.", HttpStatus.BAD_REQUEST);
        }
    }

    // Updating existing expense
    @PutMapping("{userId}/{expenseId}")
    public ResponseEntity<?> updateExpense(@PathVariable String userId, @PathVariable String expenseId, @RequestBody Expense updatedExpense) {
        try {
            // Update expense
            expenseService.updateExpense(userId, expenseId, updatedExpense);

            // Return success message
            return new ResponseEntity<>("Expense updated successfully.", HttpStatus.OK);
        } catch (IllegalStateException e) {
            // User or expense not found
            return new ResponseEntity<>("Unable to update expense.", HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            // Invalid expense details
            return new ResponseEntity<>("Invalid expense. Ensure amount is greater than 0 and description is not empty.", HttpStatus.BAD_REQUEST);
        }
    }

    // Deleting specific expense
    @DeleteMapping("{userId}/{expenseId}")
    public ResponseEntity<?> deleteExpense(@PathVariable String userId, @PathVariable String expenseId) {
        try {
            // Delete expense
            expenseService.deleteExpense(userId, expenseId);

            // Return success message
            return new ResponseEntity<>("Expense deleted successfully.", HttpStatus.OK);
        } catch (IllegalStateException e) {
            // User or expense not found
            return new ResponseEntity<>("Invalid expense/user ID.", HttpStatus.BAD_REQUEST);
        }
    }
}
