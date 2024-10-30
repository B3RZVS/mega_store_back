package com.tpi_pais.mega_store.auth.mapper;

import com.tpi_pais.mega_store.auth.dto.RolDTO;
import com.tpi_pais.mega_store.auth.model.Rol;

public class RolMapper {
    public static RolDTO toDTO(Rol model) {
        RolDTO dto = new RolDTO();
        dto.setId(model.getId());
        dto.setNombre(model.getNombre());
        dto.setFechaEliminacion(model.getFechaEliminacion());
        return dto;
    }

    public static Rol toEntity(RolDTO dto) {
        Rol model = new Rol();
        model.setId(dto.getId());
        model.setNombre(dto.getNombre());
        model.setFechaEliminacion(dto.getFechaEliminacion());
        return model;
    }
}
