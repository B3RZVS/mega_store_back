package com.tpi_pais.mega_store.products.dto;

import com.tpi_pais.mega_store.utils.StringUtils;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TalleDTO {

    private Integer id;
    private String nombre;
    private LocalDateTime fechaEliminacion;

    public boolean noTieneNombre (){
        return this.getNombre() == null || this.getNombre() == "";
    }

    public void capitalizarNombre (){
        StringUtils stringUtils = new StringUtils();

        this.setNombre(stringUtils.capitalizeWords(this.getNombre()));
    }

}