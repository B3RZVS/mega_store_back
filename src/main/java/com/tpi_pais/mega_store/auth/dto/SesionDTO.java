package com.tpi_pais.mega_store.auth.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SesionDTO {

    private Integer id;
    private String token;
    private LocalDateTime fechaEliminacion;
    private LocalDateTime fechaCreacion;
    private Integer usuarioId;
}
