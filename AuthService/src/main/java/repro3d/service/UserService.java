package repro3d.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import repro3d.model.User;
import repro3d.repository.UserRepository;
import repro3d.repository.RoleRepository;
import repro3d.utils.ApiResponse;

import java.util.List;
import java.util.Optional;

/**
 * Service class for handling operations related to {@link User} entities.
 * <p>
 * This service encapsulates the business logic for creating, retrieving, updating, and deleting users,
 * interacting with the {@link UserRepository} and {@link RoleRepository} to perform these operations.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository; // Repository to check the existence of roles

    /**
     * Constructs a {@code UserService} with the necessary repositories.
     *
     * @param userRepository The repository used for user data operations.
     * @param roleRepository The repository used for role data operations.
     */
    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Creates and saves a new user in the repository.
     *
     * @param user The user to be created and saved.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the result of the create operation.
     */
    public ResponseEntity<ApiResponse> createUser(User user) {
        if (user.getRole() != null && !roleRepository.existsById(user.getRole().getRoleId())) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Role not found for ID: " + user.getRole().getRoleId(), null));
        }
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(new ApiResponse(true, "User created successfully", savedUser));
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the user if found, or an error message otherwise.
     */
    public ResponseEntity<ApiResponse> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(new ApiResponse(true, "User found", user.get()));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "User not found for ID: " + id, null));
        }
    }

    /**
     * Retrieves all users from the repository.
     *
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the list of all users.
     */
    public ResponseEntity<ApiResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(new ApiResponse(true, "Users retrieved successfully", users));
    }

    /**
     * Updates an existing user identified by their ID with new details.
     *
     * @param id The ID of the user to update.
     * @param userDetails The new details for the user.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the updated user, or an error message if the user was not found.
     */
    public ResponseEntity<ApiResponse> updateUser(Long id, User userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    if (userDetails.getRole() != null && !roleRepository.existsById(userDetails.getRole().getRoleId())) {
                        return ResponseEntity.badRequest().body(new ApiResponse(false, "Role not found for ID: " + userDetails.getRole().getRoleId(), null));
                    }
                    user.setEmail(userDetails.getEmail());
                    user.setBillingAddress(userDetails.getBillingAddress());
                    user.setRole(userDetails.getRole());
                    userRepository.save(user);
                    return ResponseEntity.ok(new ApiResponse(true, "User updated successfully", user));
                }).orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "User not found for ID: " + id, null)));
    }

    /**
     * Deletes a user from the repository identified by their ID.
     *
     * @param id The ID of the user to delete.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating the result of the delete operation.
     */
    public ResponseEntity<ApiResponse> deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse(true, "User deleted successfully", null));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "User not found for ID: " + id, null));
        }
    }
}
