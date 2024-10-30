package com.tpi_pais.mega_store.auth.mapper;

import com.tpi_pais.mega_store.auth.dto.UsuarioDTO;
import com.tpi_pais.mega_store.auth.model.Usuario;
import com.tpi_pais.mega_store.auth.model.Rol;

public class UsuarioMapper {
    public static UsuarioDTO toDTO(Usuario model) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(model.getId());
        dto.setNombre(model.getNombre());
        dto.setEmail(model.getEmail());
        dto.setTelefono(model.getTelefono());
        dto.setDireccionEnvio(model.getDireccionEnvio());
        dto.setFechaCreacion(model.getFechaCreacion());
        dto.setCodigoVerificacion(model.getCodigoVerificacion());
        dto.setVerificado(model.getVerificado());
        dto.setPassword(model.getPassword());
        dto.setFechaEliminacion(model.getFechaEliminacion());

        // Asegurarse de que el rol no sea nulo antes de acceder a su ID
        if (model.getRol() != null) {
            dto.setRolId(Long.valueOf(model.getRol().getId()));
        }
        return dto;
    }

    public static Usuario toEntity(UsuarioDTO dto) {
        Usuario model = new Usuario();
        model.setId(dto.getId());
        model.setNombre(dto.getNombre());
        model.setEmail(dto.getEmail());
        model.setTelefono(dto.getTelefono());
        model.setDireccionEnvio(dto.getDireccionEnvio());
        model.setFechaCreacion(dto.getFechaCreacion());
        model.setCodigoVerificacion(dto.getCodigoVerificacion());
        model.setVerificado(dto.getVerificado());
        model.setPassword(dto.getPassword());
        model.setFechaEliminacion(dto.getFechaEliminacion());

        // Convertir el ID de rol en un objeto Rol si es necesario
        if (dto.getRolId() != null) {
            Rol rol = new Rol();
            rol.setId(Math.toIntExact(dto.getRolId()));
            model.setRol(rol);
        }
        return model;
    }
}
