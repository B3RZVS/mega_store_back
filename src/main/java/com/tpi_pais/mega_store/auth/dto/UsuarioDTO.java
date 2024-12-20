package com.tpi_pais.mega_store.auth.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UsuarioDTO {

    private Integer id;
    private String nombre;
    private String email;
    private String telefono;
    private String direccionEnvio;
    private LocalDateTime fechaCreacion;
    private String codigoVerificacion;
    private Boolean verificado;
    private String password;
    private Integer rolId;  // Para simplificar la representación del rol
    private LocalDateTime fechaEliminacion;
}
