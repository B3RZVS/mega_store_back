package com.tpi_pais.mega_store.products.mapper;

import com.tpi_pais.mega_store.products.dto.HistorialPrecioDTO;
import com.tpi_pais.mega_store.products.model.HistorialPrecio;

public class HistorialPrecioMapper {
    public static HistorialPrecioDTO toDTO(HistorialPrecio model) {
        HistorialPrecioDTO dto = new HistorialPrecioDTO();
        dto.setId(model.getId());
        dto.setPrecio(model.getPrecio());
        dto.setFecha(model.getFecha());
        dto.setUsuarioId(model.getUsuario().getId());
        dto.setProductoId(model.getProducto().getId());
        return dto;
    }

    public static HistorialPrecio toEntity(HistorialPrecioDTO dto) {
        HistorialPrecio model = new HistorialPrecio();
        model.setId(dto.getId());
        model.setPrecio(dto.getPrecio());
        model.setFecha(dto.getFecha());
        model.setUsuario(null);
        model.setProducto(null);
        return model;
    }
}
