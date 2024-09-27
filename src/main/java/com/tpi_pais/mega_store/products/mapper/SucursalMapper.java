package com.tpi_pais.mega_store.products.mapper;

import com.tpi_pais.mega_store.products.dto.SucursalDTO;
import com.tpi_pais.mega_store.products.model.Sucursal;

public class SucursalMapper {
    public static SucursalDTO toDTO(Sucursal model) {
        SucursalDTO dto = new SucursalDTO();
        dto.setId(model.getId());
        dto.setNombre(model.getNombre());
        dto.setFechaEliminacion(model.getFechaEliminacion());
        return dto;
    }

    public static Sucursal toEntity(SucursalDTO dto) {
        Sucursal model = new Sucursal();
        model.setId(dto.getId());
        model.setNombre(dto.getNombre());
        model.setFechaEliminacion(dto.getFechaEliminacion());
        return model;
    }
}
