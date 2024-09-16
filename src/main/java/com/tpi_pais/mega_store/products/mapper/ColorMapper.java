package com.tpi_pais.mega_store.products.mapper;

import com.tpi_pais.mega_store.products.dto.ColorDTO;
import com.tpi_pais.mega_store.products.model.Color;

public class ColorMapper {
    public static ColorDTO toDTO(Color model) {
        ColorDTO dto = new ColorDTO();
        dto.setId(model.getId());
        dto.setNombre(model.getNombre());
        dto.setFechaEliminacion(model.getFechaEliminacion());
        return dto;
    }

    public static Color toEntity(ColorDTO dto) {
        Color model = new Color();
        model.setId(dto.getId());
        model.setNombre(dto.getNombre());
        model.setFechaEliminacion(dto.getFechaEliminacion());
        return model;
    }
}
