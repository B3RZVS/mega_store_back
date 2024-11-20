package com.tpi_pais.mega_store.products.dto;

import com.tpi_pais.mega_store.products.model.Producto;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class HistorialPrecioDTO {

    private Integer id;
    private Double precio;
    private LocalDateTime fecha;
    private Integer usuarioId;
    private Integer productoId;

    // Constructor por defecto
    public HistorialPrecioDTO() {
    }

    // Constructor parametrizado
    public HistorialPrecioDTO(Integer id, Double precio, LocalDateTime fecha, Integer usuarioId, Integer productoId) {
        this.id = id;
        this.precio = precio;
        this.fecha = fecha;
        this.usuarioId = usuarioId;
        this.productoId = productoId;
    }
}
