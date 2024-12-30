package com.tpi_pais.mega_store.exception;

import com.tpi_pais.mega_store.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Servicio para construir respuestas estándar de la API.
 * Facilita la creación de respuestas consistentes tanto para casos de éxito como para errores.
 */
@Service
public class ResponseService {

    /**
     * Crea una respuesta exitosa con datos incluidos.
     *
     * @param data    Los datos que se devolverán en la respuesta.
     * @param message Mensaje descriptivo del resultado de la operación.
     * @return Una entidad de respuesta con el formato de éxito.
     */
    public ResponseEntity<ApiResponse<Object>> successResponse(Object data, String message) {
        ApiResponse<Object> response = new ApiResponse<>(
                200,          // Código HTTP para éxito
                message,      // Mensaje de éxito
                data,         // Datos relacionados con la operación
                null          // No se incluyen errores
        );
        return ResponseEntity.ok(response); // Retorna un estado 200 OK
    }

    /**
     * Crea una respuesta exitosa sin datos (útil para operaciones como eliminaciones o actualizaciones).
     *
     * @param message Mensaje descriptivo del resultado de la operación.
     * @return Una entidad de respuesta con el formato de éxito.
     */
    public ResponseEntity<ApiResponse<Object>> successResponse(String message) {
        ApiResponse<Object> response = new ApiResponse<>(
                200,          // Código HTTP para éxito
                message,      // Mensaje de éxito
                null,         // Sin datos relacionados
                null          // No se incluyen errores
        );
        return ResponseEntity.ok(response); // Retorna un estado 200 OK
    }
}
