package io.github.pavanbobade01.finance.module.user.dto;

import io.github.pavanbobade01.finance.module.user.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateRoleRequest {

    @NotNull(message = "Role is required")
    private Role role;
}