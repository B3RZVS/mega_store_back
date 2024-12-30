package com.tpi_pais.mega_store.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase genérica para estructurar las respuestas de la API.
 *
 * @param <T> El tipo de datos que contendrá la respuesta.
 */
@Data
@AllArgsConstructor // Genera un constructor con todos los atributos.
@NoArgsConstructor  // Genera un constructor sin argumentos.
public class ApiResponse<T> {
    private int status;        // Código de estado HTTP de la respuesta (ej. 200, 404, 500).
    private String message;    // Mensaje que describe el resultado (ej. "Operación exitosa" o "Error").
    private T data;            // Datos que se devuelven como resultado exitoso de la operación.
    private String errors;     // Detalles de errores en caso de que la operación falle.
}
