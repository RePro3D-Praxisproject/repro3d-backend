import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import repro3d.model.Role;
import repro3d.repository.RoleRepository;
import repro3d.service.RoleService;
import repro3d.utils.ApiResponse;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    private Role role;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        role = new Role();
        role.setRoleId(1L);
        role.setRoleName("Admin");
    }

    @Test
    void createRole_Success() {
        when(roleRepository.save(any(Role.class))).thenReturn(role);
        ResponseEntity<ApiResponse> response = roleService.createRole(role);
        assertTrue(response.getBody().isSuccess());
        assertEquals("Role created successfully", response.getBody().getMessage());
        assertEquals(role, response.getBody().getData());
    }

    @Test
    void getRoleById_Found() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        ResponseEntity<ApiResponse> response = roleService.getRoleById(1L);
        assertTrue(response.getBody().isSuccess());
        assertEquals("Role found", response.getBody().getMessage());
        assertEquals(role, response.getBody().getData());
    }

    @Test
    void getRoleById_NotFound() {
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<ApiResponse> response = roleService.getRoleById(1L);
        assertFalse(response.getBody().isSuccess());
        assertEquals("Role not found for ID: 1", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void getAllRoles_Success() {
        Role anotherRole = new Role(2L, "User");
        when(roleRepository.findAll()).thenReturn(Arrays.asList(role, anotherRole));
        ResponseEntity<ApiResponse> response = roleService.getAllRoles();
        assertTrue(response.getBody().isSuccess());
        assertEquals("Roles retrieved successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertTrue(((Iterable<?>) response.getBody().getData()).spliterator().estimateSize() > 1);
    }

    @Test
    void updateRole_Success() {
        Role updatedRoleDetails = new Role(1L, "UpdatedAdmin");
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(roleRepository.save(any(Role.class))).thenReturn(updatedRoleDetails);

        ResponseEntity<ApiResponse> response = roleService.updateRole(1L, updatedRoleDetails);
        assertTrue(response.getBody().isSuccess());
        assertEquals("Role updated successfully", response.getBody().getMessage());
        assertEquals(updatedRoleDetails.getRoleName(), ((Role) response.getBody().getData()).getRoleName());
    }

    @Test
    void updateRole_NotFound() {
        Role updatedRoleDetails = new Role(1L, "UpdatedAdmin");
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse> response = roleService.updateRole(1L, updatedRoleDetails);
        assertFalse(response.getBody().isSuccess());
        assertEquals("Role not found for ID: 1", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void deleteRole_Success() {
        when(roleRepository.existsById(1L)).thenReturn(true);
        doNothing().when(roleRepository).deleteById(1L);

        ResponseEntity<ApiResponse> response = roleService.deleteRole(1L);
        assertTrue(response.getBody().isSuccess());
        assertEquals("Role deleted successfully", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void deleteRole_NotFound() {
        when(roleRepository.existsById(1L)).thenReturn(false);

        ResponseEntity<ApiResponse> response = roleService.deleteRole(1L);
        assertFalse(response.getBody().isSuccess());
        assertEquals("Role not found for ID: 1", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }
}
