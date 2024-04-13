package jed.portfolioexpense.expenseApplication.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    @Indexed(unique = true)
    private String username;

    private String password;

    private List<Expense> expenses;

    private LocalDateTime createdAt;

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.expenses = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
    }
}
