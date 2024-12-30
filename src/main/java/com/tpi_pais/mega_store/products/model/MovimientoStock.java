package com.tpi_pais.mega_store.products.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Clase que representa un movimiento de stock dentro del sistema Mega Store.
 * Permite registrar ingresos y egresos de productos del inventario.
 */
@Entity
@Table(name = "movimiento_stock")
@Data
public class MovimientoStock {

    /**
     * Identificador único del movimiento de stock.
     * Generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Cantidad de productos involucrados en el movimiento.
     */
    @Column(name = "cantidad")
    private Integer cantidad;

    /**
     * Indica si el movimiento es un egreso (`true`) o ingreso (`false`).
     */
    @Column(name = "es_egreso")
    private Boolean esEgreso;

    /**
     * Producto relacionado con el movimiento de stock.
     * Representa una relación muchos-a-uno con la tabla de productos.
     */
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    /**
     * Fecha en la que se realizó el movimiento de stock.
     * Se asigna automáticamente al momento de crear el registro.
     */
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    /**
     * Sucursal relacionada con el movimiento de stock.
     * Representa una relación muchos-a-uno con la tabla de sucursales.
     */
    @ManyToOne
    @JoinColumn(name = "sucursal_id", nullable = true)
    private Sucursal sucursal;

    /**
     * Método que verifica si el movimiento es un ingreso.
     *
     * @return `true` si el movimiento es un ingreso, `false` si es un egreso.
     */
    public Boolean esIngreso() {
        return (!this.esEgreso);
    }

    /**
     *Método de ciclo de vida de JPA que se ejecuta antes de persistir el objeto.
     * Asigna la fecha de creación al momento actual.
     */
    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
    }
}
