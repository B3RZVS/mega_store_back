package com.tpi_pais.mega_store.products.dto;

import com.tpi_pais.mega_store.utils.StringUtils;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * DTO (Data Transfer Object) para la entidad Producto.
 * Representa los datos de un producto en el sistema, incluyendo detalles como el nombre, precio,
 * stock disponible y categoría, entre otros.
 */
@Data
public class ProductoDTO {

    private Integer id; // Identificador único del producto.
    private String nombre; // Nombre del producto.
    private LocalDateTime fechaEliminacion; // Fecha de eliminación lógica del producto (si aplica).
    private BigDecimal precio; // Precio del producto.
    private BigDecimal peso; // Peso del producto, generalmente utilizado para el cálculo de envío.
    private Integer stockMedio; // Stock promedio del producto.
    private Integer stockMinimo; // Stock mínimo permitido para este producto.
    private Integer stockActual; // Stock actual disponible del producto.
    private String foto; // URL o nombre del archivo de la foto del producto.
    private String descripcion; // Descripción del producto, incluyendo detalles adicionales.

    private Integer categoriaId; // Identificador de la categoría a la que pertenece el producto.
    private Integer sucursalId; // Identificador de la sucursal donde se encuentra el producto.
    private Integer marcaId; // Identificador de la marca del producto.
    private Integer talleId; // Identificador del talle del producto (si aplica).
    private Integer colorId; // Identificador del color del producto (si aplica).

    private MultipartFile imagen;

    /**
     * Método para verificar si el nombre del producto está vacío o es nulo.
     *
     * @return true si el nombre es nulo o está vacío, false si tiene un valor.
     */
    public boolean noTieneNombre() {
        return this.getNombre() == null || this.getNombre().isEmpty();
    }

    /**
     * Método para capitalizar la primera letra de cada palabra en el nombre del producto.
     * Utiliza la clase StringUtils para aplicar esta transformación.
     */
    public void capitalizarNombre() {
        StringUtils stringUtils = new StringUtils();
        this.setNombre(stringUtils.capitalizeWords(this.getNombre()));
    }
}
