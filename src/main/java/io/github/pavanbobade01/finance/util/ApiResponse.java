package io.github.pavanbobade01.finance.util;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private T data;
    private Object message;

    // ─────────────────────────────────────────
    // SUCCESS RESPONSES
    // ─────────────────────────────────────────

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .message(null)
                .build();
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .message(message)
                .build();
    }

    // ─────────────────────────────────────────
    // ERROR RESPONSES
    // ─────────────────────────────────────────

    public static <T> ApiResponse<T> error(Object message) {
        return ApiResponse.<T>builder()
                .success(false)
                .data(null)
                .message(message)
                .build();
    }
}