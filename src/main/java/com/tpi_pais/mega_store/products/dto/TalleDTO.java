package com.tpi_pais.mega_store.products.dto;

import com.tpi_pais.mega_store.utils.StringUtils;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) para la entidad Talle.
 * Representa los datos de un talle de producto en el sistema, incluyendo su nombre y la fecha de eliminación.
 */
@Data
public class TalleDTO {

    private Integer id; // Identificador único del talle.
    private String nombre; // Nombre del talle (por ejemplo, S, M, L, XL).
    private LocalDateTime fechaEliminacion; // Fecha de eliminación lógica del talle (si aplica).

    /**
     * Método para verificar si el nombre del talle está vacío o es nulo.
     *
     * @return true si el nombre es nulo o está vacío, false si tiene un valor.
     */
    public boolean noTieneNombre() {
        return this.getNombre() == null || this.getNombre().isEmpty();
    }

    /**
     * Método para capitalizar la primera letra de cada palabra en el nombre del talle.
     * Utiliza la clase StringUtils para aplicar esta transformación.
     */
    public void capitalizarNombre() {
        StringUtils stringUtils = new StringUtils();
        this.setNombre(stringUtils.capitalizeWords(this.getNombre()));
    }
}
