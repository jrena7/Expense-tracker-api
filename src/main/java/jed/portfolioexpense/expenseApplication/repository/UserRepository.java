package jed.portfolioexpense.expenseApplication.repository;

import jed.portfolioexpense.expenseApplication.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
    User findUserById(String userId);
    boolean existsByEmail(String username);
    boolean existsByUsername(String username);
}
