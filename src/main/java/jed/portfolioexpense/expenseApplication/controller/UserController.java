package jed.portfolioexpense.expenseApplication.controller;

import jed.portfolioexpense.expenseApplication.model.User;
import jed.portfolioexpense.expenseApplication.service.UserService;
import jed.portfolioexpense.expenseApplication.dto.UpdateEmail;
import jed.portfolioexpense.expenseApplication.dto.UpdatePassword;
import jed.portfolioexpense.expenseApplication.dto.UpdateUsername;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Creating users
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody User userToCreate) {
        try {
            // Create user
            User user = userService.createUser(userToCreate);

            // Return user details
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            // User already exists
            return new ResponseEntity<>("Existing user with Username or Email.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Invalid user details
            return new ResponseEntity<>("Invalid user details. Ensure email/ username/ password are valid.", HttpStatus.BAD_REQUEST);
        }
    }

    // Retrieving user details
    @GetMapping("/{userId}")
    private ResponseEntity<?> getUserInfo(@PathVariable String userId) {
        try {
            // Get user details
            User user = userService.getUserInfo(userId); // Get user details

            // Return user details on success
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (IllegalStateException e) {
            // User does not exist
            return new ResponseEntity<>("User cannot be found.", HttpStatus.BAD_REQUEST);
        }
    }

    // Updating user email
    @PutMapping("/{userId}/updateEmail")
    public ResponseEntity<?> updateEmail(@PathVariable String userId, @RequestBody UpdateEmail request) {
        try {
            // Update user email
            userService.updateEmail(userId, request.updatedEmail());

            // Return success message
            return new ResponseEntity<>("Email updated successfully.", HttpStatus.OK);
        } catch (IllegalStateException e) {
            // User cannot be found or does not exist
            return new ResponseEntity<>("User does not exist.", HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            // Invalid email details, incorrect format or already in use
            return new ResponseEntity<>("Unable to update email with provided details.", HttpStatus.BAD_REQUEST);
        }
    }

    // Updating user username
    @PutMapping("/{userId}/updateUsername")
    public ResponseEntity<?> updateUsername(@PathVariable String userId, @RequestBody UpdateUsername request) {
        try {
            // Update user username
            userService.updateUsername(userId, request.updatedUsername());

            // Return success message
            return new ResponseEntity<>("Username updated successfully.", HttpStatus.OK);
        } catch (IllegalStateException e) {
            // User cannot be found or does not exist
            return new ResponseEntity<>("User does not exist.", HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            // Invalid username details, incorrect format or already in use
            return new ResponseEntity<>("Unable to update username with provided details.", HttpStatus.BAD_REQUEST);
        }
    }

    // Updating user password
    @PutMapping("/{userId}/updatePassword")
    public ResponseEntity<?> updatePassword(@PathVariable String userId, @RequestBody UpdatePassword request) {
        try {
            // Update user password
            userService.updatePassword(userId, request.updatedPassword());

            // Return success message
            return new ResponseEntity<>("Password updated successfully.", HttpStatus.OK);
        } catch (IllegalStateException e) {
            // User cannot be found or does not exist
            return new ResponseEntity<>("User does not exist.", HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            // Invalid password details, incorrect format or currently in use
            return new ResponseEntity<>("Unable to update password with provided details.", HttpStatus.BAD_REQUEST);
        }
    }

    // Deleting user
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        try {
            // Delete user
            userService.deleteUser(userId);

            // Return success message
            return new ResponseEntity<>("User deleted successfully.", HttpStatus.OK);
        } catch (IllegalStateException e) {
            // User cannot be found or does not exist
            return new ResponseEntity<>("User does not exist.", HttpStatus.BAD_REQUEST);
        }
    }
}
