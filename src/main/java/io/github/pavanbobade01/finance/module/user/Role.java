package io.github.pavanbobade01.finance.module.user;

public enum Role {
    VIEWER,
    ANALYST,
    ADMIN;

    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}