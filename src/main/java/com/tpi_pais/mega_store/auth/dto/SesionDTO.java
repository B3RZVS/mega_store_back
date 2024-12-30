package com.tpi_pais.mega_store.auth.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO para representar una sesión de usuario en el sistema.
 */
@Data
public class SesionDTO {

    /**
     * ID de la sesión.
     */
    private Integer id;

    /**
     * Token asociado a la sesión de usuario.
     */
    private String token;

    /**
     * Fecha de eliminación de la sesión. Si es null, significa que la sesión no ha sido eliminada.
     */
    private LocalDateTime fechaEliminacion;

    /**
     * Fecha de creación de la sesión.
     */
    private LocalDateTime fechaCreacion;

    /**
     * ID del usuario asociado a la sesión.
     */
    private Integer usuario_id;

    /**
     * Email del usuario asociado a la sesión.
     */
    private String usuario_email;

    /**
     * Nombre del usuario asociado a la sesión.
     */
    private String usuario_nombre;

    /**
     * ID del rol asociado a la sesión del usuario.
     */
    private Integer rol_id;

    /**
     * Nombre del rol asociado a la sesión del usuario.
     */
    private String rol_nombre;

}
