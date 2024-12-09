package com.tpi_pais.mega_store.exception;

import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

// Anotación que permite manejar excepciones de forma global en toda la aplicación
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Manejo de excepciones de tipo BadRequestException (código 400)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequest(BadRequestException ex) {
        // Construye la respuesta en un formato estándar utilizando ApiResponse
        ApiResponse<Object> response = new ApiResponse<>(
                400, // Código de estado HTTP
                "Error: Bad Request", // Mensaje general del error
                null, // Datos adicionales (ninguno en este caso)
                ex.getMessage() // Mensaje específico de la excepción
        );
        // Retorna una respuesta HTTP con código 400
        return ResponseEntity.badRequest().body(response);
    }

    // Manejo de excepciones de tipo NotFoundException (código 404)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFound(NotFoundException ex) {
        ApiResponse<Object> response = new ApiResponse<>(
                404, // Código de estado HTTP
                "Error: Not Found", // Mensaje general del error
                null,
                ex.getMessage() // Mensaje específico de la excepción
        );
        // Retorna una respuesta HTTP con código 404
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Manejo de excepciones de tipo ForbiddenException (código 403)
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Object> handleForbidden(ForbiddenException ex) {
        ApiResponse<Object> response = new ApiResponse<>(
                403, // Código de estado HTTP
                "Error: Forbidden", // Mensaje general del error
                null,
                ex.getMessage() // Mensaje específico de la excepción
        );
        // Retorna una respuesta HTTP con código 403
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    // Manejo de excepciones de tipo UnauthorizedException (código 401)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorized(UnauthorizedException ex) {
        ApiResponse<Object> response = new ApiResponse<>(
                401, // Código de estado HTTP
                "Error: Unauthorized", // Mensaje general del error
                null,
                ex.getMessage() // Mensaje específico de la excepción
        );
        // Retorna una respuesta HTTP con código 401
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    // Manejo de excepciones de tipo ConflictException (código 409)
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> handleConflict(ConflictException ex) {
        ApiResponse<Object> response = new ApiResponse<>(
                409, // Código de estado HTTP
                "Error: Conflict", // Mensaje general del error
                null,
                ex.getMessage() // Mensaje específico de la excepción
        );
        // Retorna una respuesta HTTP con código 409
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // Manejo de excepciones de tipo MethodArgumentTypeMismatchException (código 400)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        // Mensaje personalizado para informar que un parámetro no tiene el tipo esperado
        String error = String.format("El parametro enviado '%s' no es del tipo esperado.", ex.getName());
        ApiResponse<Object> response = new ApiResponse<>(
                400, // Código de estado HTTP
                "Error de tipo de argumento", // Mensaje general del error
                null,
                error // Mensaje específico del error
        );
        // Retorna una respuesta HTTP con código 400
        return ResponseEntity.badRequest().body(response);
    }

    // Manejo de excepciones genéricas no controladas (código 500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex) {
        ApiResponse<Object> response = new ApiResponse<>(
                500, // Código de estado HTTP
                "Error: Internal Server Error", // Mensaje general del error
                null,
                ex.getMessage() // Mensaje específico de la excepción
        );
        // Retorna una respuesta HTTP con código 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

