package com.tpi_pais.mega_store.auth.mapper;

import com.tpi_pais.mega_store.auth.dto.UsuarioDTO;
import com.tpi_pais.mega_store.auth.model.Usuario;
import com.tpi_pais.mega_store.auth.model.Rol;

/**
 * Mapper para convertir entre el modelo de datos Usuario y su representación DTO (UsuarioDTO).
 */
public class UsuarioMapper {

    // Constructor privado para evitar la instanciación de la clase
    private UsuarioMapper() {}

    /**
     * Convierte un objeto Usuario (modelo de datos) a un objeto UsuarioDTO.
     *
     * @param model El objeto Usuario que se va a convertir.
     * @return El objeto UsuarioDTO resultante.
     */
    public static UsuarioDTO toDTO(Usuario model) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(model.getId()); // Se copia el ID del usuario
        dto.setNombre(model.getNombre()); // Se copia el nombre del usuario
        dto.setEmail(model.getEmail()); // Se copia el email del usuario
        dto.setTelefono(model.getTelefono()); // Se copia el teléfono del usuario
        dto.setDireccionEnvio(model.getDireccionEnvio()); // Se copia la dirección de envío del usuario
        dto.setFechaCreacion(model.getFechaCreacion()); // Se copia la fecha de creación del usuario
        dto.setCodigoVerificacion(model.getCodigoVerificacion()); // Se copia el código de verificación del usuario
        dto.setVerificado(model.getVerificado()); // Se copia el estado de verificación del usuario
        dto.setPassword(""); // Se establece el password vacío, para no exponerlo en el DTO
        dto.setFechaEliminacion(model.getFechaEliminacion()); // Se copia la fecha de eliminación del usuario

        // Asegurarse de que el rol no sea nulo antes de acceder a su ID
        if (model.getRol() != null) {
            dto.setRolId(model.getRol().getId()); // Se copia el ID del rol si el rol no es nulo
        }
        return dto; // Retorna el objeto UsuarioDTO creado
    }

    /**
     * Convierte un objeto UsuarioDTO a un objeto Usuario (modelo de datos).
     *
     * @param dto El objeto UsuarioDTO que se va a convertir.
     * @return El objeto Usuario resultante.
     */
    public static Usuario toEntity(UsuarioDTO dto) {
        Usuario model = new Usuario();
        model.setId(dto.getId()); // Se copia el ID del DTO
        model.setNombre(dto.getNombre()); // Se copia el nombre del DTO
        model.setEmail(dto.getEmail()); // Se copia el email del DTO
        model.setTelefono(dto.getTelefono()); // Se copia el teléfono del DTO
        model.setDireccionEnvio(dto.getDireccionEnvio()); // Se copia la dirección de envío del DTO
        model.setFechaCreacion(dto.getFechaCreacion()); // Se copia la fecha de creación del DTO
        model.setCodigoVerificacion(dto.getCodigoVerificacion()); // Se copia el código de verificación del DTO
        model.setVerificado(dto.getVerificado()); // Se copia el estado de verificación del DTO
        model.setPassword(dto.getPassword()); // Se copia la contraseña del DTO
        model.setFechaEliminacion(dto.getFechaEliminacion()); // Se copia la fecha de eliminación del DTO

        // Convertir el ID de rol en un objeto Rol si es necesario
        if (dto.getRolId() != null) {
            Rol rol = new Rol();
            rol.setId(Math.toIntExact(dto.getRolId())); // Se convierte el ID en un objeto Rol
            model.setRol(rol); // Se asigna el objeto Rol al usuario
        }
        return model; // Retorna el objeto Usuario creado
    }
}
