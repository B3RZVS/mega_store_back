package com.tpi_pais.mega_store.products.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.ToString;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa un producto en el sistema Mega Store.
 * Incluye detalles como nombre, precio, descripción, stock y relaciones con otras entidades.
 */
@Entity
@Table(name = "productos")
@Data
@ToString
public class Producto {

    /**
     * Identificador único del producto.
     * Generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre del producto.
     * Debe tener entre 1 y 100 caracteres.
     */
    @Size(min = 1, max = 100, message = "El nombre del producto debe tener entre 1 y 100 caracteres")
    @NotNull(message = "El nombre es obligatorio")
    @Column(name = "nombre")
    private String nombre;

    /**
     * Descripción del producto.
     * No debe exceder los 100 caracteres.
     */
    @Size(max = 100, message = "La descripción no debe exceder los 100 caracteres")
    @Column(name = "descripcion")
    private String descripcion;

    /**
     * Precio del producto.
     * Debe ser mayor que 0.
     */
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que 0")
    @Column(name = "precio")
    private BigDecimal precio;

    /**
     * Peso del producto.
     * Debe ser mayor que 0.
     */
    @NotNull(message = "El peso es obligatorio")
    @DecimalMin(value = "0.01", message = "El peso debe ser mayor que 0")
    @Column(name = "peso")
    private BigDecimal peso;

    /**
     * Foto del producto.
     * Se almacena la ruta o nombre del archivo de la foto.
     */
    @Column(name = "foto")
    private String foto;

    /**
     * Stock mínimo requerido del producto.
     * Debe ser mayor o igual a 0.
     */
    @Min(value = 0, message = "El stock mínimo debe ser mayor o igual a 0")
    @Column(name = "stock_minimo")
    private Integer stockMinimo;

    /**
     * Stock medio del producto.
     * Debe ser mayor que 0 y mayor que el stock mínimo.
     */
    @Min(value = 1, message = "El stock medio debe ser mayor que 0 y mayor que el stock mínimo")
    @Column(name = "stock_medio")
    private Integer stockMedio;

    /**
     * Stock actual disponible del producto.
     * Valor por defecto es 0.
     */
    @Column(name = "stock_actual", nullable = false)
    private Integer stockActual = 0;

    /**
     * Relación con la entidad Categoría.
     * El producto debe pertenecer a una categoría específica.
     */
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    /**
     * Relación con la entidad Marca.
     * El producto debe estar asociado con una marca.
     */
    @ManyToOne
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;

    /**
     * Relación con la entidad Talle.
     * El producto debe tener un tamaño (talle) específico.
     */
    @ManyToOne
    @JoinColumn(name = "talle_id", nullable = false)
    private Talle talle;

    /**
     * Relación con la entidad Color.
     * El producto debe tener un color específico.
     */
    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;

    /**
     * Fecha de creación del producto.
     * Se asigna automáticamente al momento de la creación.
     */
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    /**
     * Fecha de eliminación del producto.
     * Usada cuando un producto es eliminado del sistema (de forma lógica).
     */
    @Column(name = "fecha_eliminacion")
    private LocalDateTime fechaEliminacion;

    /**
     * Usuario que eliminó el producto.
     */
    @Column(name = "usuario_eliminacion")
    private String usuarioEliminacion;

    /**
     * Elimina el producto (de forma lógica) estableciendo la fecha de eliminación y el usuario que la realiza.
     *
     * @param usuario El usuario que realiza la eliminación.
     */
    public void eliminar(String usuario) {
        this.fechaEliminacion = LocalDateTime.now();
        this.usuarioEliminacion = usuario;
    }

    /**
     * Recupera un producto eliminado (restaurando su estado).
     */
    public void recuperar() {
        this.fechaEliminacion = null;
        this.usuarioEliminacion = null;
    }

    /**
     * Verifica si el producto ha sido eliminado.
     *
     * @return true si el producto está eliminado, false si no.
     */
    public boolean esEliminado() {
        return this.fechaEliminacion != null;
    }
}

