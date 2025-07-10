package org.example.postservice.utils;

import org.example.postservice.model.dto.APIResponse;
import org.example.postservice.model.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;

public class GenerateResponse {
    public static ResponseEntity<?> generateSuccessResponse(Integer status, String message, Object data) {
        return ResponseEntity.status(status).body(
                new APIResponse<>(
                        status,
                        message,
                        data
                )
        );
    }

    public static ResponseEntity<?> generateErrorResponse(Integer status, String message) {
        return ResponseEntity.status(status).body(
                new ErrorResponse(
                        status,
                        message
                )
        );

    }
}
