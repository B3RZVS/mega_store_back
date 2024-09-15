package com.tpi_pais.mega_store.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private int status;        // Código de estado HTTP
    private String message;    // Mensaje de estado (éxito o error)
    private T data;            // Datos relevantes que se devuelven
    private String errors;     // En caso de error, detalles del mismo
}

