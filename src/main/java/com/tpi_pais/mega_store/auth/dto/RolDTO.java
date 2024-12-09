package com.tpi_pais.mega_store.auth.dto;

import com.tpi_pais.mega_store.utils.StringUtils;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RolDTO {

    private Integer id;
    private String nombre;
    private LocalDateTime fechaEliminacion;

    /**
     * Verifica si el atributo 'nombre' del rol está vacío o es nulo.
     *
     * @return true si el nombre es nulo o vacío, false en caso contrario.
     */
    public boolean noTieneNombre () {
        return this.getNombre() == null || this.getNombre().isEmpty();
    }

    /**
     * Capitaliza el nombre del rol utilizando el método de utilidad StringUtils.
     * Este método transforma el nombre del rol a formato capitalizado (cada palabra con la primera letra en mayúscula).
     */
    public void capitalizarNombre () {
        StringUtils stringUtils = new StringUtils();

        this.setNombre(stringUtils.capitalizeWords(this.getNombre()));
    }
}
