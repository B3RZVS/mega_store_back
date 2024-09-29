package com.tpi_pais.mega_store.products.mapper;

import com.tpi_pais.mega_store.products.dto.MarcaDTO;
import com.tpi_pais.mega_store.products.model.Marca;

public class MarcaMapper {
    public static MarcaDTO toDTO(Marca model) {
        MarcaDTO dto = new MarcaDTO();
        dto.setId(model.getId());
        dto.setNombre(model.getNombre());
        dto.setFechaEliminacion(model.getFechaEliminacion());
        return dto;
    }

    public static Marca toEntity(MarcaDTO dto) {
        Marca model = new Marca();
        model.setId(dto.getId());
        model.setNombre(dto.getNombre());
        model.setFechaEliminacion(dto.getFechaEliminacion());
        return model;
    }
}
