package com.tpi_pais.mega_store.products.dto;

import com.tpi_pais.mega_store.utils.StringUtils;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) para la entidad Color.
 * Representa los datos que serán transferidos en las operaciones relacionadas con los colores.
 */
@Data
public class ColorDTO {

    private Integer id; // Identificador único del color.
    private String nombre; // Nombre del color.
    private LocalDateTime fechaEliminacion; // Fecha en la que el color fue eliminado (si aplica).

    /**
     * Verifica si el nombre del color está vacío o es nulo.
     *
     * @return true si el nombre es nulo o vacío, false en caso contrario.
     */
    public boolean noTieneNombre () {
        return this.getNombre() == null || this.getNombre().isEmpty(); // Verifica si el nombre es nulo o vacío
    }

    /**
     * Capitaliza el nombre del color.
     * Utiliza la clase StringUtils para capitalizar cada palabra en el nombre.
     */
    public void capitalizarNombre () {
        StringUtils stringUtils = new StringUtils(); // Crea una instancia de StringUtils para procesar el nombre
        this.setNombre(stringUtils.capitalizeWords(this.getNombre())); // Capitaliza el nombre y lo asigna a la propiedad 'nombre'
    }
}
