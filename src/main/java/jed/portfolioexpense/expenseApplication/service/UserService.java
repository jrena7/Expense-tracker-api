package jed.portfolioexpense.expenseApplication.service;

import jed.portfolioexpense.expenseApplication.model.User;

public interface UserService {
    User createUser(User userToCreate) throws Exception;
    User getUserInfo(String userId);
    void updateUsername(String userId, String updatedUsername);
    void updateEmail(String userId, String updatedUsername);
    void updatePassword(String userId, String updatedUsername);
    void deleteUser(String userId);
}
