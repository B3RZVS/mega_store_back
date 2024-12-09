package com.tpi_pais.mega_store.auth.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * DTO para representar los datos de un usuario en el sistema.
 */
@Data
public class UsuarioDTO {

    /**
     * ID único del usuario.
     */
    private Integer id;

    /**
     * Nombre del usuario.
     */
    private String nombre;

    /**
     * Email del usuario.
     */
    private String email;

    /**
     * Número de teléfono del usuario.
     */
    private String telefono;

    /**
     * Dirección de envío del usuario.
     */
    private String direccionEnvio;

    /**
     * Fecha de creación de la cuenta del usuario.
     */
    private LocalDateTime fechaCreacion;

    /**
     * Código de verificación asociado al usuario para confirmar su identidad.
     */
    private String codigoVerificacion;

    /**
     * Estado de verificación del usuario. Si es true, el usuario está verificado.
     */
    private Boolean verificado;

    /**
     * Contraseña del usuario (en texto plano o procesada según la lógica de seguridad).
     */
    private String password;

    /**
     * ID del rol asociado al usuario. Facilita la representación del rol sin cargar todo el objeto.
     */
    private Integer rolId;

    /**
     * Fecha de eliminación del usuario. Si es null, significa que el usuario no ha sido eliminado.
     */
    private LocalDateTime fechaEliminacion;
}
