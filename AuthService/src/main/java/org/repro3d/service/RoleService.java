package org.repro3d.service;

import org.repro3d.model.Role;
import org.repro3d.repository.RoleRepository;
import org.repro3d.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for handling operations related to {@link Role} entities.
 * This service encapsulates the business logic for creating, retrieving, updating, and deleting roles,
 * interacting with the {@link RoleRepository} to perform these operations.
 */
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    /**
     * Constructs a role service with the necessary role repository.
     *
     * @param roleRepository The repository used for role data operations.
     */
    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Creates and saves a new role in the repository.
     *
     * @param role The role to be created and saved.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the result of the create operation.
     */
    public ResponseEntity<ApiResponse> createRole(Role role) {
        Role savedRole = roleRepository.save(role);
        return ResponseEntity.ok(new ApiResponse(true, "Role created successfully", savedRole));
    }

    /**
     * Retrieves a role by its ID.
     *
     * @param id The ID of the role to retrieve.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the role if found, or an error message otherwise.
     */
    public ResponseEntity<ApiResponse> getRoleById(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            return ResponseEntity.ok(new ApiResponse(true, "Role found", role.get()));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "Role not found for ID: " + id, null));
        }
    }

    /**
     * Retrieves all roles in the repository.
     *
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the list of all roles.
     */
    public ResponseEntity<ApiResponse> getAllRoles() {
        Iterable<Role> roles = roleRepository.findAll();
        return ResponseEntity.ok(new ApiResponse(true, "Roles retrieved successfully", roles));
    }

    /**
     * Updates an existing role identified by its ID with new details.
     *
     * @param id          The ID of the role to update.
     * @param roleDetails The new details for the role.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the updated role, or an error message if the role was not found.
     */
    public ResponseEntity<ApiResponse> updateRole(Long id, Role roleDetails) {
        return roleRepository.findById(id)
                .map(role -> {
                    role.setRoleName(roleDetails.getRoleName());
                    roleRepository.save(role);
                    return ResponseEntity.ok(new ApiResponse(true, "Role updated successfully", role));
                }).orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "Role not found for ID: " + id, null)));
    }

    /**
     * Deletes a role from the repository identified by its ID.
     *
     * @param id The ID of the role to delete.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating the result of the delete operation.
     */
    public ResponseEntity<ApiResponse> deleteRole(Long id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse(true, "Role deleted successfully", null));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "Role not found for ID: " + id, null));
        }
    }
}
