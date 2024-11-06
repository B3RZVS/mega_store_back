package com.tpi_pais.mega_store.auth.mapper;

import com.tpi_pais.mega_store.auth.dto.SesionDTO;
import com.tpi_pais.mega_store.auth.model.Sesion;

public class SesionMapper {

    public static SesionDTO toDTO(Sesion model) {

        SesionDTO dto = new SesionDTO();
        dto.setId(model.getId());
        dto.setUsuarioId(model.getUsuario().getId());
        dto.setToken(model.getToken());
        dto.setFechaCreacion(model.getFechaCreacion());
        dto.setFechaEliminacion(model.getFechaEliminacion());
        return dto;
    }

    public static Sesion toEntity(SesionDTO dto) {

        Sesion model = new Sesion();
        model.setId(dto.getId());
        //model.setUsuario(dto.getUsuarioId());
        model.setToken(dto.getToken());
        model.setFechaCreacion(dto.getFechaCreacion());
        model.setFechaEliminacion(dto.getFechaEliminacion());
        return model;
    }
}
