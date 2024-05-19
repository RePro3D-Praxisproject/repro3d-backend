package org.repro3d.controller;

import org.repro3d.model.Role;
import org.repro3d.service.RoleService;
import org.repro3d.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing {@link Role} entities.
 * Provides endpoints for CRUD operations on roles, including creation, retrieval,
 * updating, and deletion of roles.
 */
@RestController
@RequestMapping("/api/role")
public class RoleController {

    private final RoleService roleService;

    /**
     * Constructs a {@code RoleController} with the necessary {@link RoleService}.
     *
     * @param roleService The service used to perform operations on roles.
     */
    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Creates a new role.
     *
     * @param role The role to create.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating
     *         the result of the create operation.
     */
    @PostMapping
    public ResponseEntity<ApiResponse> createRole(@RequestBody Role role) {
        return roleService.createRole(role);
    }

    /**
     * Retrieves a role by its ID.
     *
     * @param id The ID of the role to retrieve.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the role
     *         if found or an error message otherwise.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    /**
     * Retrieves all roles.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with a list
     *         of all roles.
     */
    @GetMapping
    public ResponseEntity<ApiResponse> getAllRoles() {
        return roleService.getAllRoles();
    }

    /**
     * Updates the details of an existing role.
     * @param id The ID of the role to update.
     * @param roleDetails The new details for the role.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating
     *         the result of the update operation.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateRole(@PathVariable Long id, @RequestBody Role roleDetails) {
        return roleService.updateRole(id, roleDetails);
    }

    /**
     * Deletes a role by its ID.
     * @param id The ID of the role to delete.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} indicating
     *         the result of the delete operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteRole(@PathVariable Long id) {
        return roleService.deleteRole(id);
    }
}
