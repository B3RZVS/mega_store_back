package com.tpi_pais.mega_store.auth.mapper;

import com.tpi_pais.mega_store.auth.dto.SesionDTO;
import com.tpi_pais.mega_store.auth.model.Rol;
import com.tpi_pais.mega_store.auth.model.Sesion;

/**
 * Mapper para convertir entre el modelo de datos Sesion y su representación DTO (SesionDTO).
 */
public class SesionMapper {

    // Constructor privado para evitar instanciación de la clase
    private SesionMapper() {}

    /**
     * Convierte un objeto Sesion (modelo de datos) a un objeto SesionDTO.
     *
     * @param model El objeto Sesion que se va a convertir.
     * @return El objeto SesionDTO resultante.
     */
    public static SesionDTO toDTO(Sesion model) {
        SesionDTO dto = new SesionDTO();
        dto.setId(model.getId()); // Se copia el ID de la sesión
        dto.setUsuario_id(model.getUsuario().getId()); // Se copia el ID del usuario asociado a la sesión
        dto.setToken(model.getToken()); // Se copia el token de la sesión
        dto.setFechaCreacion(model.getFechaCreacion()); // Se copia la fecha de creación
        dto.setFechaEliminacion(model.getFechaEliminacion()); // Se copia la fecha de eliminación
        return dto; // Retorna el objeto SesionDTO creado
    }

    /**
     * Convierte un objeto Sesion (modelo de datos) a un objeto SesionDTO con información adicional del usuario y rol.
     *
     * @param model El objeto Sesion que se va a convertir.
     * @param aux Parámetro adicional que no se usa directamente, pero se incluye para mantener la firma del método.
     * @return El objeto SesionDTO resultante con datos adicionales sobre el usuario y rol.
     */
    public static SesionDTO toDTO(Sesion model, Integer aux) {
        SesionDTO dto = new SesionDTO();
        dto.setId(model.getId()); // Se copia el ID de la sesión
        dto.setUsuario_id(model.getUsuario().getId()); // Se copia el ID del usuario asociado
        dto.setUsuario_email(model.getUsuario().getEmail()); // Se copia el email del usuario
        dto.setUsuario_nombre(model.getUsuario().getNombre()); // Se copia el nombre del usuario
        dto.setToken(model.getToken()); // Se copia el token de la sesión
        dto.setFechaCreacion(model.getFechaCreacion()); // Se copia la fecha de creación
        dto.setFechaEliminacion(model.getFechaEliminacion()); // Se copia la fecha de eliminación
        dto.setRol_id(model.getUsuario().getRol().getId()); // Se copia el ID del rol del usuario
        dto.setRol_nombre(model.getUsuario().getRol().getNombre()); // Se copia el nombre del rol del usuario
        return dto; // Retorna el objeto SesionDTO con los datos adicionales del usuario y rol
    }

    /**
     * Convierte un objeto SesionDTO a un objeto Sesion (modelo de datos).
     *
     * @param dto El objeto SesionDTO que se va a convertir.
     * @return El objeto Sesion resultante.
     */
    public static Sesion toEntity(SesionDTO dto) {
        Sesion model = new Sesion();
        model.setId(dto.getId()); // Se copia el ID del DTO
        model.setToken(dto.getToken()); // Se copia el token del DTO
        model.setFechaCreacion(dto.getFechaCreacion()); // Se copia la fecha de creación del DTO
        model.setFechaEliminacion(dto.getFechaEliminacion()); // Se copia la fecha de eliminación del DTO
        return model; // Retorna el objeto Sesion creado
    }
}
