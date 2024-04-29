package org.repro3d.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.repro3d.model.User;
import org.repro3d.service.UserService;
import org.repro3d.utils.ApiResponse;

/**
 * Controller for managing {@link User} entities.
 * Provides endpoints for CRUD operations on users, including creation, retrieval,
 * updating, and deletion of users.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    /**
     * Constructs a {@code UserController} with the necessary {@link UserService}.
     * @param userService The service used to perform operations on users.
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Creates a new user.
     * @param user The user to create.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating
     *         the result of the create operation.
     */
    @PostMapping
    public ResponseEntity<ApiResponse> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    /**
     * Retrieves a user by its ID.
     * @param id The ID of the user to retrieve.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the user
     *         if found, or an error message otherwise.
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    /**
     * Retrieves all users.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with a list
     *         of all users.
     */
    @GetMapping
    public ResponseEntity<ApiResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Retrieves a user by its email address.
     * @param email The ID of the user to retrieve.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the user
     *         if found, or an error message otherwise.
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse> getUserByEmail(@PathVariable String email) { return userService.getUserByEmail(email); }

    /**
     * Updates the details of an existing user.
     * @param id The ID of the user to update.
     * @param userDetails The new details for the user.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating
     *         the result of the update operation.
     */
    @PutMapping("/id/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userService.updateUser(id, userDetails);
    }

    /**
     * Deletes a user by its ID.
     * @param id The ID of the user to delete.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating
     *         the result of the delete operation.
     */
    @DeleteMapping("/id/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
