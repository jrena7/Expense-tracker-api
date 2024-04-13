package jed.portfolioexpense.expenseApplication.service;

import jed.portfolioexpense.expenseApplication.model.User;
import jed.portfolioexpense.expenseApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create user
    @Override
    public User createUser(User userToCreate) throws Exception {
        // User information to register
        String email = userToCreate.getEmail();
        String username = userToCreate.getUsername();
        String password = userToCreate.getPassword();

        if (userRepository.existsByEmail(email) || userRepository.existsByUsername(username)) {
            // User already exists
            throw new IllegalStateException();
        } else if (isValidEmail(email) && isValidUsername(username) && isValidPassword(password)) {
            // Create user if details are valid
            userToCreate.setPassword(passwordEncoder.encode(password));
            userRepository.save(userToCreate);
        } else {
            if (!isValidEmail(email)) {
                throw new IllegalArgumentException("Invalid email format");
            }

            if (!isValidUsername(username)) {
                throw new IllegalArgumentException("Invalid username format");
            }

            if (!isValidPassword(password)) {
                throw new IllegalArgumentException("Invalid password format");
            }
        }
        return userToCreate;
    }

    // Retrieve user details
    @Override
    public User getUserInfo(String userId) {
        // Return user details if found
        return findUser(userId);
    }

    // Update user details (email, username, password)
    @Override
    public void updateEmail(String userId, String updatedEmail) {
        // User to be updated
        User user = findUser(userId);

        // Check if updated email is valid and not already in use
        if (updatedEmail != null && isValidEmail(updatedEmail)
                && !userRepository.existsByEmail(updatedEmail)) {
            user.setEmail(updatedEmail); // Update email
        } else {
            // Else throw exception if invalid
            throw new IllegalArgumentException();
        }

        // Save updated user information
        userRepository.save(user);
    }

    @Override
    public void updateUsername(String userId, String updatedUsername) {
        // User to be updated
        User user = findUser(userId);

        // Check if updated username is valid and not already in use
        if (updatedUsername != null && isValidUsername(updatedUsername)
                && !userRepository.existsByUsername(updatedUsername)) {
            user.setUsername(updatedUsername);
        } else {
            // Else throw exception if invalid
            throw new IllegalArgumentException();
        }

        // Save updated user information
        userRepository.save(user);
    }

    @Override
    public void updatePassword(String userId, String updatedPassword) {
        // User to be updated
        User user = findUser(userId);

        // Current password
        String currentPassword = user.getPassword();

        // Check if updated password is valid and not the same as current password
        if (updatedPassword != null && isValidPassword(updatedPassword)
                && !passwordEncoder.matches(updatedPassword, currentPassword)) {
            user.setPassword(passwordEncoder.encode(updatedPassword)); // Update password
        } else {
            // Else throw exception if invalid
            throw new IllegalArgumentException();
        }

        // Save updated user information
        userRepository.save(user);
    }

    // Delete user
    @Override
    public void deleteUser(String userId) {
        // User to be deleted
        User user = findUser(userId);

        // Delete user if found
        userRepository.delete(user);
    }

    // Email, username and password validation
    private static Boolean isValidEmail(String email) {
        // Only allows emails with a-z, A-Z, 0-9, and special characters: _+&*-
        // Followed by an @ symbol
        // Followed by "." and min 2 i.e .ie, .eu etc
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    private static Boolean isValidUsername(String username) {
        // Only allows usernames with a-z, A-Z, 0-9 or underscore
        // Min 3 and max 20 characters
        return username.matches("^[a-zA-Z0-9_]{3,20}$");
    }

    private static Boolean isValidPassword(String password) {
        // Only allows passwords with a-z, A-Z, 0-9 or special characters
        // Min 8 and max 20 characters
        return password.matches("^[a-zA-Z0-9_!@#$%^&*()]{8,20}$");
    }

    // Method to find a user
    User findUser(String userId) {
        // Find user by id
        User user = userRepository.findUserById(userId);

        // Throw exception if user not found
        if (user == null) {
            throw new IllegalStateException();
        }

        // Return found user
        return user;
    }
}
