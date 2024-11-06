package com.tpi_pais.mega_store.products.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.ToString;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "productos")
@Data
@ToString
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 1, max = 100, message = "El nombre del producto debe tener entre 1 y 100 caracteres")
    @NotNull(message = "El nombre es obligatorio")
    @Column(name = "nombre")
    private String nombre;

    @Size(max = 500, message = "La descripción no debe exceder los 500 caracteres")
    @Column(name = "descripcion")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que 0")
    @Column(name = "precio")
    private BigDecimal precio;

    @NotNull(message = "El peso es obligatorio")
    @DecimalMin(value = "0.01", message = "El peso debe ser mayor que 0")
    @Column(name = "peso")
    private BigDecimal peso;

    @Column(name = "foto")
    private String foto; // La ruta o nombre del archivo de la foto se almacenará aquí


    @Min(value = 0, message = "El stock mínimo debe ser mayor o igual a 0")
    @Column(name = "stock_minimo")
    private Integer stockMinimo;

    @Min(value = 1, message = "El stock medio debe ser mayor que 0 y mayor que el stock mínimo")
    @Column(name = "stock_medio")
    private Integer stockMedio;

    @Column(name = "stock_actual", nullable = false)
    private Integer stockActual = 0; // Valor por defecto es 0

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "sucursal_id", nullable = false)
    private Sucursal sucursal;

    @ManyToOne
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "talle_id", nullable = false)
    private Talle talle;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "fecha_eliminacion")
    private LocalDateTime fechaEliminacion;

    @Column(name = "usuario_eliminacion")
    private String usuarioEliminacion; // El usuario que elimina el producto

    public void eliminar(String usuario) {
        this.fechaEliminacion = LocalDateTime.now();
        this.usuarioEliminacion = usuario;
    }

    public void recuperar() {
        this.fechaEliminacion = null;
        this.usuarioEliminacion = null;
    }

    public boolean esEliminado() {
        return this.fechaEliminacion != null;
    }
}
