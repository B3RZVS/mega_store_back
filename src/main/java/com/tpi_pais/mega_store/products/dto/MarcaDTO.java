package com.tpi_pais.mega_store.products.dto;

import com.tpi_pais.mega_store.utils.StringUtils;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) para la entidad Marca.
 * Representa los datos de una marca, incluyendo su nombre y la fecha de eliminación.
 */
@Data
public class MarcaDTO {

    private Integer id; // Identificador único de la marca.
    private String nombre; // Nombre de la marca.
    private LocalDateTime fechaEliminacion; // Fecha en que la marca fue eliminada (si aplica).

    /**
     * Método que verifica si el nombre de la marca está vacío o nulo.
     *
     * @return true si el nombre es nulo o vacío, false en caso contrario.
     */
    public boolean noTieneNombre() {
        return this.getNombre() == null || this.getNombre().isEmpty();
    }

    /**
     * Método que capitaliza el nombre de la marca, convirtiendo la primera letra de cada palabra a mayúscula.
     */
    public void capitalizarNombre() {
        StringUtils stringUtils = new StringUtils();
        this.setNombre(stringUtils.capitalizeWords(this.getNombre()));
    }
}
