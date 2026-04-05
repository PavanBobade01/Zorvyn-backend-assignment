package io.github.pavanbobade01.finance.module.user;

import io.github.pavanbobade01.finance.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // ─────────────────────────────────────────
    // GET USER BY ID
    // ─────────────────────────────────────────

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    public ApiResponse<User> getUserById(@PathVariable String id) {
        return ApiResponse.success(userService.getUserById(id));
    }

    // ─────────────────────────────────────────
    // GET ALL USERS (PAGINATION)
    // ─────────────────────────────────────────

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Page<User>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.success(userService.getAllUsers(pageable));
    }

    // ─────────────────────────────────────────
    // GET USERS BY ROLE
    // ─────────────────────────────────────────

    @GetMapping("/role/{role}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Page<User>> getUsersByRole(
            @PathVariable Role role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.success(userService.getUsersByRole(role, pageable));
    }

    // ─────────────────────────────────────────
    // GET ACTIVE USERS
    // ─────────────────────────────────────────

    @GetMapping("/active")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Page<User>> getActiveUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.success(userService.getActiveUsers(pageable));
    }

    // ─────────────────────────────────────────
    // UPDATE USER ROLE (ADMIN ONLY)
    // ─────────────────────────────────────────

    @PatchMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> updateUserRole(
            @PathVariable String id,
            @RequestParam Role role
    ) {
        userService.updateUserRole(id, role);
        return ApiResponse.success("User role updated successfully");
    }

    // ─────────────────────────────────────────
    // ACTIVATE / DEACTIVATE USER
    // ─────────────────────────────────────────

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> updateUserStatus(
            @PathVariable String id,
            @RequestParam boolean active
    ) {
        userService.updateUserStatus(id, active);
        return ApiResponse.success("User status updated successfully");
    }

    // ─────────────────────────────────────────
    // DELETE USER (SOFT DELETE)
    // ─────────────────────────────────────────

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ApiResponse.success("User deactivated successfully");
    }

    // ─────────────────────────────────────────
    // SEARCH USERS
    // ─────────────────────────────────────────

    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Page<User>> searchUsers(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.success(userService.searchUsers(keyword, pageable));
    }

    // ─────────────────────────────────────────
    // DASHBOARD COUNTS (ADMIN)
    // ─────────────────────────────────────────

    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Object> getUserStats() {

        var stats = new Object() {
            public final long active = userService.countActiveUsers();
            public final long inactive = userService.countInactiveUsers();
            public final long admins = userService.countByRole(Role.ADMIN);
            public final long analysts = userService.countByRole(Role.ANALYST);
            public final long viewers = userService.countByRole(Role.VIEWER);
        };

        return ApiResponse.success(stats);
    }
}