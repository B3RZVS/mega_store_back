package com.tpi_pais.mega_store.products.mapper;

import com.tpi_pais.mega_store.products.dto.TalleDTO;
import com.tpi_pais.mega_store.products.model.Talle;

public class TalleMapper {
    public static TalleDTO toDTO(Talle model) {
        TalleDTO dto = new TalleDTO();
        dto.setId(model.getId());
        dto.setNombre(model.getNombre());
        dto.setFechaEliminacion(model.getFechaEliminacion());
        return dto;
    }

    public static Talle toEntity(TalleDTO dto) {
        Talle model = new Talle();
        model.setId(dto.getId());
        model.setNombre(dto.getNombre());
        model.setFechaEliminacion(dto.getFechaEliminacion());
        return model;
    }
}
