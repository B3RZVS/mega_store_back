package com.tpi_pais.mega_store.products.dto;

import com.tpi_pais.mega_store.products.model.Sucursal;
import lombok.Data;

@Data
public class MovimientoDTO {
    private Integer idSucursal;
    private Integer cantidad;
    private Sucursal sucursal;
}
