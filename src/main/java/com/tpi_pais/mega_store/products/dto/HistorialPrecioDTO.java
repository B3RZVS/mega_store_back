package com.tpi_pais.mega_store.products.dto;

import com.tpi_pais.mega_store.products.model.Producto;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) para la entidad HistorialPrecio.
 * Representa los datos relacionados con el historial de precios de los productos.
 */
@Data
public class HistorialPrecioDTO {

    private Integer id; // Identificador único del historial de precio.
    private BigDecimal precio; // Precio del producto en un momento específico.
    private LocalDateTime fecha; // Fecha en la que el precio fue registrado.
    private Integer usuarioId; // Identificador del usuario que realizó el cambio de precio.
    private Integer productoId; // Identificador del producto cuyo precio ha sido registrado.

    /**
     * Constructor por defecto. Inicializa un objeto de HistorialPrecioDTO sin valores.
     */
    public HistorialPrecioDTO() {
    }

    /**
     * Constructor parametrizado.
     *
     * @param id Identificador único del historial de precio.
     * @param precio Precio del producto en el momento del historial.
     * @param fecha Fecha del cambio de precio.
     * @param usuarioId Identificador del usuario que hizo el cambio.
     * @param productoId Identificador del producto cuyo precio ha sido registrado.
     */
    public HistorialPrecioDTO(Integer id, BigDecimal precio, LocalDateTime fecha, Integer usuarioId, Integer productoId) {
        this.id = id;
        this.precio = precio;
        this.fecha = fecha;
        this.usuarioId = usuarioId;
        this.productoId = productoId;
    }
}
