package com.tpi_pais.mega_store.products.dto;

import com.tpi_pais.mega_store.utils.StringUtils;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) para la entidad Sucursal.
 * Representa los datos de una sucursal en el sistema, incluyendo detalles como su nombre y fecha de eliminación.
 */
@Data
public class SucursalDTO {

    private Integer id; // Identificador único de la sucursal.
    private String nombre; // Nombre de la sucursal.
    private LocalDateTime fechaEliminacion; // Fecha de eliminación lógica de la sucursal (si aplica).

    /**
     * Método para verificar si el nombre de la sucursal está vacío o es nulo.
     *
     * @return true si el nombre es nulo o está vacío, false si tiene un valor.
     */
    public boolean noTieneNombre() {
        return this.getNombre() == null || this.getNombre().isEmpty();
    }

    /**
     * Método para capitalizar la primera letra de cada palabra en el nombre de la sucursal.
     * Utiliza la clase StringUtils para aplicar esta transformación.
     */
    public void capitalizarNombre() {
        StringUtils stringUtils = new StringUtils();
        this.setNombre(stringUtils.capitalizeWords(this.getNombre()));
    }
}
