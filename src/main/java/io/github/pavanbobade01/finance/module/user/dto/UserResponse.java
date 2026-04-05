package io.github.pavanbobade01.finance.module.user.dto;

import io.github.pavanbobade01.finance.module.user.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {

    private String id;
    private String name;
    private String email;
    private Role role;
    private boolean active;
    private LocalDateTime createdAt;
}