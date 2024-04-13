package jed.portfolioexpense.expenseApplication.dto;

public record ExpenseRequest(
        String description,
        double amount
) {
}
