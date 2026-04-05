package io.github.pavanbobade01.finance.module.user;

import io.github.pavanbobade01.finance.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ─────────────────────────────────────────
    // CREATE USER (Registration handled in AuthService)
    // ─────────────────────────────────────────

    @Transactional
    public User createUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.VIEWER); // default role
        user.setActive(true);

        return userRepository.save(user);
    }

    // ─────────────────────────────────────────
    // GET USER BY ID
    // ─────────────────────────────────────────

    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    // ─────────────────────────────────────────
    // GET ALL USERS (Pagination)
    // ─────────────────────────────────────────

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    // ─────────────────────────────────────────
    // GET USERS BY ROLE
    // ─────────────────────────────────────────

    public Page<User> getUsersByRole(Role role, Pageable pageable) {
        return userRepository.findByRole(role, pageable);
    }

    // ─────────────────────────────────────────
    // GET ACTIVE USERS
    // ─────────────────────────────────────────

    public Page<User> getActiveUsers(Pageable pageable) {
        return userRepository.findByActive(true, pageable);
    }

    // ─────────────────────────────────────────
    // UPDATE ROLE (ADMIN ONLY)
    // ─────────────────────────────────────────

    @Transactional
    public void updateUserRole(String userId, Role role) {

        int updated = userRepository.updateRoleById(userId, role);

        if (updated == 0) {
            throw new ResourceNotFoundException("User not found");
        }
    }

    // ─────────────────────────────────────────
    // ACTIVATE / DEACTIVATE USER
    // ─────────────────────────────────────────

    @Transactional
    public void updateUserStatus(String userId, boolean active) {

        int updated = userRepository.updateStatusById(userId, active);

        if (updated == 0) {
            throw new ResourceNotFoundException("User not found");
        }
    }

    // ─────────────────────────────────────────
    // DELETE USER (SOFT DELETE)
    // ─────────────────────────────────────────

    @Transactional
    public void deleteUser(String userId) {

        User user = getUserById(userId);
        user.setActive(false);

        userRepository.save(user);
    }

    // ─────────────────────────────────────────
    // SEARCH USERS
    // ─────────────────────────────────────────

    public Page<User> searchUsers(String keyword, Pageable pageable) {
        return userRepository.searchByKeyword(keyword, pageable);
    }

    // ─────────────────────────────────────────
    // DASHBOARD STATS
    // ─────────────────────────────────────────

    public long countByRole(Role role) {
        return userRepository.countByRole(role);
    }

    public long countActiveUsers() {
        return userRepository.countByActiveTrue();
    }

    public long countInactiveUsers() {
        return userRepository.countByActiveFalse();
    }
}