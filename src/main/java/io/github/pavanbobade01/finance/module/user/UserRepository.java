package io.github.pavanbobade01.finance.module.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // ─────────────────────────────────────────
    // Basic Finders
    // ─────────────────────────────────────────

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndActiveTrue(String email);

    boolean existsByEmail(String email);

    // ─────────────────────────────────────────
    // Find by Role
    // ─────────────────────────────────────────

    List<User> findByRole(Role role);

    Page<User> findByRole(Role role, Pageable pageable);

    // ─────────────────────────────────────────
    // Find by Status (active / inactive)
    // ─────────────────────────────────────────

    List<User> findByActiveTrue();

    List<User> findByActiveFalse();

    Page<User> findByActive(boolean active, Pageable pageable);

    // ─────────────────────────────────────────
    // Find by Role AND Status
    // ─────────────────────────────────────────

    List<User> findByRoleAndActive(Role role, boolean active);

    Page<User> findByRoleAndActive(Role role, boolean active, Pageable pageable);

    // ─────────────────────────────────────────
    // Update Role (Admin action)
    // ─────────────────────────────────────────

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE User u SET u.role = :role WHERE u.id = :id")
    int updateRoleById(@Param("id") String id, @Param("role") Role role);

    // ─────────────────────────────────────────
    // Update Status: Activate / Deactivate
    // ─────────────────────────────────────────

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE User u SET u.active = :active WHERE u.id = :id")
    int updateStatusById(@Param("id") String id, @Param("active") boolean active);

    // ─────────────────────────────────────────
    // Count Queries (Dashboard/Admin Stats)
    // ─────────────────────────────────────────

    long countByRole(Role role);

    long countByActiveTrue();

    long countByActiveFalse();

    // ─────────────────────────────────────────
    // Search (Admin Panel)
    // ─────────────────────────────────────────

    @Query("""
        SELECT u FROM User u
        WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    Page<User> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    // ─────────────────────────────────────────
    // Clean Pagination (Spring handles it)
    // ─────────────────────────────────────────

    Page<User> findAll(Pageable pageable);

    // ─────────────────────────────────────────
    // Active users (latest first)
    // ─────────────────────────────────────────

    Page<User> findByActiveTrueOrderByCreatedAtDesc(Pageable pageable);
}