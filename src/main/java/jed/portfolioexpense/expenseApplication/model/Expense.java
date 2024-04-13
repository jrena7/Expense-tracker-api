package jed.portfolioexpense.expenseApplication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "expenses")
@NoArgsConstructor
@AllArgsConstructor
public class Expense {
    @Id
    private String id;

    private double amount;

    private String description;

    @DBRef
    private User user;

    public Expense(double amount, String description) {
        this.amount = amount;
        this.description = description;
    }
}
