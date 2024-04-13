package jed.portfolioexpense.expenseApplication.repository;

import jed.portfolioexpense.expenseApplication.model.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends MongoRepository<Expense, String>{
    Expense findByDescription(String description);

    Expense findExpenseById(String expenseId);
}