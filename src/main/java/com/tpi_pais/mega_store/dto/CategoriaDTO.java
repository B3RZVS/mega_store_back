package com.tpi_pais.mega_store.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoriaDTO {

    private Integer id;
    private String nombre;
    private LocalDateTime fechaEliminacion;

}
