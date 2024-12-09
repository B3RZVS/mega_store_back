package com.tpi_pais.mega_store.auth.mapper;

import com.tpi_pais.mega_store.auth.dto.RolDTO;
import com.tpi_pais.mega_store.auth.model.Rol;

/**
 * Mapper para convertir entre el modelo de datos Rol y su representación DTO (RolDTO).
 */
public class RolMapper {

    // Constructor privado para evitar instanciación de la clase
    private RolMapper() {}

    /**
     * Convierte un objeto Rol (modelo de datos) a un objeto RolDTO.
     *
     * @param model El objeto Rol que se va a convertir.
     * @return El objeto RolDTO resultante.
     */
    public static RolDTO toDTO(Rol model) {
        RolDTO dto = new RolDTO();
        dto.setId(model.getId()); // Se copia el ID del Rol
        dto.setNombre(model.getNombre()); // Se copia el nombre del Rol
        dto.setFechaEliminacion(model.getFechaEliminacion()); // Se copia la fecha de eliminación
        return dto; // Retorna el objeto RolDTO creado
    }

    /**
     * Convierte un objeto RolDTO a un objeto Rol (modelo de datos).
     *
     * @param dto El objeto RolDTO que se va a convertir.
     * @return El objeto Rol resultante.
     */
    public static Rol toEntity(RolDTO dto) {
        Rol model = new Rol();
        model.setId(dto.getId()); // Se copia el ID del DTO
        model.setNombre(dto.getNombre()); // Se copia el nombre del DTO
        model.setFechaEliminacion(dto.getFechaEliminacion()); // Se copia la fecha de eliminación
        return model; // Retorna el objeto Rol creado
    }
}
