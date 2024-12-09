package com.tpi_pais.mega_store.products.dto;

import com.tpi_pais.mega_store.utils.StringUtils;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) para la entidad Categoria.
 * Representa los datos que serán transferidos en las operaciones relacionadas con las categorías.
 */
@Data
public class CategoriaDTO {

    private Integer id; // Identificador único de la categoría.
    private String nombre; // Nombre de la categoría.
    private LocalDateTime fechaEliminacion; // Fecha en la que la categoría fue eliminada (si aplica).

    /**
     * Verifica si el nombre de la categoría está vacío o es nulo.
     *
     * @return true si el nombre es nulo o vacío, false en caso contrario.
     */
    public boolean noTieneNombre () {
        return this.getNombre() == null || this.getNombre().isEmpty(); // Verifica si el nombre es nulo o vacío
    }

    /**
     * Capitaliza el nombre de la categoría.
     * Utiliza la clase StringUtils para capitalizar cada palabra en el nombre.
     */
    public void capitalizarNombre () {
        StringUtils stringUtils = new StringUtils(); // Crea una instancia de StringUtils para procesar el nombre
        this.setNombre(stringUtils.capitalizeWords(this.getNombre())); // Capitaliza el nombre y lo asigna a la propiedad 'nombre'
    }

}
