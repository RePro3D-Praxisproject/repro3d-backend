import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.repro3d.model.Role;
import org.repro3d.model.User;
import org.repro3d.repository.RoleRepository;
import org.repro3d.repository.UserRepository;
import org.repro3d.service.UserService;
import org.repro3d.utils.ApiResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private Role role;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        role = new Role(1L, "Admin");
        user = new User();
        user.setUserId(1L);
        user.setEmail("test@example.com");
        user.setRole(role);
        user.setBillingAddress("123 Test St");
        user.setPasswordHash("hashed_password");
    }

    @Test
    void createUser_Success() {
        when(roleRepository.existsById(any())).thenReturn(true);
        when(userRepository.save(any(User.class))).thenReturn(user);
        ResponseEntity<ApiResponse> response = userService.createUser(user);
        assertTrue(response.getBody().isSuccess());
        assertEquals("User created successfully", response.getBody().getMessage());
        assertEquals(user, response.getBody().getData());
    }

    @Test
    void createUser_FailRoleNotFound() {
        when(roleRepository.existsById(any())).thenReturn(false);
        ResponseEntity<ApiResponse> response = userService.createUser(user);
        assertFalse(response.getBody().isSuccess());
        assertEquals("Role not found for ID: 1", response.getBody().getMessage());
    }

    @Test
    void getUserById_Found() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        ResponseEntity<ApiResponse> response = userService.getUserById(1L);
        assertTrue(response.getBody().isSuccess());
        assertEquals("User found", response.getBody().getMessage());
        assertEquals(user, response.getBody().getData());
    }

    @Test
    void getUserById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<ApiResponse> response = userService.getUserById(1L);
        assertFalse(response.getBody().isSuccess());
        assertEquals("User not found for ID: 1", response.getBody().getMessage());
    }

    @Test
    void getUserByEmail_Found() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        ResponseEntity<ApiResponse> response = userService.getUserByEmail("test@example.com");
        assertTrue(response.getBody().isSuccess());
        assertEquals("User found", response.getBody().getMessage());
        assertEquals(user, response.getBody().getData());
    }

    @Test
    void getUserByEmail_NotFound() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        ResponseEntity<ApiResponse> response = userService.getUserByEmail("test@example.com");
        assertFalse(response.getBody().isSuccess());
        assertEquals("User not found for email: test@example.com", response.getBody().getMessage());
    }

    @Test
    void getAllUsers_Success() {
        List<User> users = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(users);
        ResponseEntity<ApiResponse> response = userService.getAllUsers();
        assertTrue(response.getBody().isSuccess());
        assertEquals("Users retrieved successfully", response.getBody().getMessage());
        assertEquals(users, response.getBody().getData());
    }

    @Test
    void updateUser_Success() {
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(roleRepository.existsById(any())).thenReturn(true);
        when(userRepository.save(any(User.class))).thenReturn(user);

        ResponseEntity<ApiResponse> response = userService.updateUser(1L, user);
        assertTrue(response.getBody().isSuccess());
        assertEquals("User updated successfully", response.getBody().getMessage());
        assertEquals(user, response.getBody().getData());
    }

    @Test
    void updateUser_NotFound() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        ResponseEntity<ApiResponse> response = userService.updateUser(1L, user);
        assertFalse(response.getBody().isSuccess());
        assertEquals("User not found for ID: 1", response.getBody().getMessage());
    }

    @Test
    void updateUser_FailRoleNotFound() {
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(roleRepository.existsById(any())).thenReturn(false);
        ResponseEntity<ApiResponse> response = userService.updateUser(1L, user);
        assertFalse(response.getBody().isSuccess());
        assertEquals("Role not found for ID: 1", response.getBody().getMessage());
    }

    @Test
    void deleteUser_Success() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);
        ResponseEntity<ApiResponse> response = userService.deleteUser(1L);
        assertTrue(response.getBody().isSuccess());
        assertEquals("User deleted successfully", response.getBody().getMessage());
    }

    @Test
    void deleteUser_NotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);
        ResponseEntity<ApiResponse> response = userService.deleteUser(1L);
        assertFalse(response.getBody().isSuccess());
        assertEquals("User not found for ID: 1", response.getBody().getMessage());
    }
}
