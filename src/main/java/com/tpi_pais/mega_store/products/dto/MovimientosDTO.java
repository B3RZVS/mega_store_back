package com.tpi_pais.mega_store.products.dto;

import lombok.Data;

import java.util.List;

@Data
public class MovimientosDTO {
    private Integer idProducto;
    private List<MovimientoDTO> listaMovimientos;
}
