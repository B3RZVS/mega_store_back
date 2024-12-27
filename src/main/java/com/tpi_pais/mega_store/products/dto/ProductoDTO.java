package com.tpi_pais.mega_store.products.dto;

import com.tpi_pais.mega_store.utils.StringUtils;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.Map;

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
    private Integer[] sucursales;

    private Integer marcaId; // Identificador de la marca del producto.
    private Integer talleId; // Identificador del talle del producto (si aplica).
    private Integer colorId; // Identificador del color del producto (si aplica).

    private MultipartFile imagen;

}
