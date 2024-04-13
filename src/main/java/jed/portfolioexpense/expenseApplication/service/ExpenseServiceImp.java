package jed.portfolioexpense.expenseApplication.service;

import jed.portfolioexpense.expenseApplication.model.Expense;
import jed.portfolioexpense.expenseApplication.model.User;
import jed.portfolioexpense.expenseApplication.repository.ExpenseRepository;
import jed.portfolioexpense.expenseApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseServiceImp implements ExpenseService{

    @Autowired
    private final ExpenseRepository expenseRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserServiceImp userService;

    public ExpenseServiceImp(ExpenseRepository expenseRepository, UserRepository userRepository, UserServiceImp userServiceImp, UserServiceImp userService) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    // Method to create/ add expense
    @Override
    public void addExpense(String userId, Expense expense) {
        // Find the user by Id
        User user = userService.findUser(userId);

        // Get amount & description expense to be added
        Double amount = expense.getAmount();
        String description = expense.getDescription();

        // Check if expense is valid
        if (validExpense(amount, description)) {
            expense.setUser(user); // Set user for expense (who the expense belongs to)
            user.getExpenses().add(expense); // Add expense to user's list of expenses
            expenseRepository.save(expense); // Save expense in expense repository
            userRepository.save(user); // Save user in user repository
        } else {
            // Throw exception if expense is invalid
            throw new IllegalArgumentException();
        }

    }

    // Method to validate expense
    private boolean validExpense(Double amount, String description) {
        // Ensures an expense amount > 0 and description must not be empty and <255 characters
        return amount != null && description != null
                && amount > 0 && !description.isEmpty()
                && description.length() <= 255;
    }

    // Method to retrieve user expenses
    @Override
    public List<Expense> getUserExpensesById(String userID) {
        User user = userService.findUser(userID);
        return user.getExpenses(); // Return list of expenses for user if found
    }

    // Method to update expense
    @Override
    public void updateExpense(String userId, String expenseId, Expense updatedExpense) {

        // Find user by ID + get their expense list
        User user = userService.findUser(userId);
        List<Expense> expenses = user.getExpenses();

        // Find expense to update by ID
        Expense expenseToUpdate = findExpense(expenseId);

        // Get updated amount & description
        double updatedAmount = updatedExpense.getAmount();
        String updatedDescription = updatedExpense.getDescription();

        // Check if expense is valid
        if (validExpense(updatedAmount, updatedDescription)) {
            // Update expense in expense repository
            expenseToUpdate.setAmount(updatedAmount);
            expenseToUpdate.setDescription(updatedDescription);
            expenseRepository.save(expenseToUpdate);

            // Update expense in user's list of expenses
            for (Expense expense : expenses) {
                if (expense.getId().equals(expenseId)) {
                    expenses.set(expenses.indexOf(expense), expenseToUpdate);
                    userRepository.save(user); // Save updated list in user repository
                    break;
                }
            }

        } else {
            // Throw exception if expense is invalid
            throw new IllegalArgumentException();
        }
    }

    // Method to find expense by ID
    public Expense findExpense(String expenseId) {
        Expense expense = expenseRepository.findExpenseById(expenseId);

        // Throw exception if expense is not found
        if (expense == null) {
            throw new IllegalStateException();
        }

        return expense;
    }

    @Override
    public void deleteExpense(String userId, String expenseId) {
        User user = userService.findUser(userId); // Find user by ID
        Expense expenseToDelete = findExpense(expenseId); // Find expense to delete by ID
        List<Expense> expenses = user.getExpenses();

        expenseRepository.delete(expenseToDelete); // Delete expense from expense repository

        for (Expense expense : expenses) {
            if (expense.getId().equals(expenseId)) {
                expenses.remove(expense); // Remove expense from user's list of expenses
                userRepository.save(user); // Save user in user repository
                break;
            }
        }
    }
}
