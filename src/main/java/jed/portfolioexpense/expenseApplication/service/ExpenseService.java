package jed.portfolioexpense.expenseApplication.service;

import jed.portfolioexpense.expenseApplication.model.Expense;
import java.util.List;

public interface ExpenseService {
        void addExpense(String userId, Expense expense);
        List<Expense> getUserExpensesById(String userID);
        void updateExpense(String userId, String expenseId , Expense expense);
        void deleteExpense(String userId, String expenseId);
}
