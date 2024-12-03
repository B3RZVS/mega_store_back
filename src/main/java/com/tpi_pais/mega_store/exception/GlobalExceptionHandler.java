package com.tpi_pais.mega_store.exception;

import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Manejo de BadRequestException (400)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequest(BadRequestException ex) {
        ApiResponse<Object> response = new ApiResponse<>(
                400,
                "Error: Bad Request",
                null,
                ex.getMessage()
        );
        return ResponseEntity.badRequest().body(response);
    }

    // Manejo de NotFoundException (404)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFound(NotFoundException ex) {
        ApiResponse<Object> response = new ApiResponse<>(
                404,
                "Error: Not Found",
                null,
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Object> handleForbidden(ForbiddenException ex) {
        ApiResponse<Object> response = new ApiResponse<>(
                403,
                "Error: Forbidden",
                null,
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorized(UnauthorizedException ex) {
        ApiResponse<Object> response = new ApiResponse<>(
                401,
                "Error: Unauthorized",
                null,
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> handleConflict(ConflictException ex) {
        ApiResponse<Object> response = new ApiResponse<>(
                409,
                "Error: Conflict",
                null,
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        // Creamos una respuesta en formato JSON con el error
        String error = String.format("El parametro enviado '%s' no es del tipo esperado.", ex.getName());
        ApiResponse<Object> response = new ApiResponse<>(
                400,
                "Error de tipo de argumento",
                null,
                error
        );
        return ResponseEntity.badRequest().body(response);
    }

    // Manejo de cualquier otra excepción no controlada (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex) {
        ApiResponse<Object> response = new ApiResponse<>(
                500,
                "Error: Internal Server Error",
                null,
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

