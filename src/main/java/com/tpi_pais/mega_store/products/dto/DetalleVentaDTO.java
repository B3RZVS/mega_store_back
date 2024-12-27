package com.tpi_pais.mega_store.products.dto;

import lombok.Data;

@Data
public class DetalleVentaDTO {
    private Integer id;
    private Integer idVenta;
    private Integer idProducto;
    private String producto;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
}
