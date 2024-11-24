package com.tpi_pais.mega_store.products.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MovimientoStockDTO {
    private Integer id;
    private Integer idProducto;
    private Integer cantidad;
    private Boolean esEgreso;
    private LocalDateTime fechaCreacion;
}
