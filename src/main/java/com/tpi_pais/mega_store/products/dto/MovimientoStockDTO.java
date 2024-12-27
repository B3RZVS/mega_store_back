package com.tpi_pais.mega_store.products.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) para la entidad MovimientoStock.
 * Representa el movimiento de stock de un producto, indicando la cantidad y si es un egreso o un ingreso.
 */
@Data
public class MovimientoStockDTO {

    private Integer id; // Identificador único del movimiento de stock.
    private Integer idProducto; // Identificador del producto asociado a este movimiento de stock.
    private Integer cantidad; // Cantidad de producto movida (puede ser positiva o negativa dependiendo del tipo de movimiento).
    private Boolean esEgreso; // Indica si el movimiento es un egreso (true) o un ingreso (false).
    private LocalDateTime fechaCreacion; // Fecha y hora en que se registró el movimiento de stock.
    private Integer idSucursal; // Identificador de la sucursal asociada al movimiento de stock.
    private String nombreSucursal; // Nombre de la sucursal asociada al movimiento de stock.
}
