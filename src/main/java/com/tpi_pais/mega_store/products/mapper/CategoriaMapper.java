package com.tpi_pais.mega_store.products.mapper;

import com.tpi_pais.mega_store.products.dto.CategoriaDTO;
import com.tpi_pais.mega_store.products.model.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {
    public static CategoriaDTO toDTO(Categoria model) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(model.getId());
        dto.setNombre(model.getNombre());
        dto.setFechaEliminacion(model.getFechaEliminacion());
        return dto;
    }

    public static Categoria toEntity(CategoriaDTO dto) {
        Categoria model = new Categoria();
        model.setId(dto.getId());
        model.setNombre(dto.getNombre());
        model.setFechaEliminacion(dto.getFechaEliminacion());
        return model;
    }
}
