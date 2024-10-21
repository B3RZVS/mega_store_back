package com.tpi_pais.mega_store.exception;

import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {

    // Método para respuestas exitosas con datos
    public ResponseEntity<ApiResponse<Object>> successResponse(Object data, String message) {
        ApiResponse<Object> response = new ApiResponse<>(
                200,
                message,
                data,
                null
        );
        return ResponseEntity.ok(response);
    }

    // Método para respuestas exitosas sin datos (como operaciones sin retorno)
    public ResponseEntity<ApiResponse<Object>> successResponse(String message) {
        ApiResponse<Object> response = new ApiResponse<>(
                200,
                message,
                null,
                null
        );
        return ResponseEntity.ok(response);
    }
}
