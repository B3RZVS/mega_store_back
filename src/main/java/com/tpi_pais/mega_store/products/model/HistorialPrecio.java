package com.tpi_pais.mega_store.products.model;

import com.tpi_pais.mega_store.auth.model.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Clase que representa el historial de precios de un producto.
 * Registra cambios en los precios de productos junto con la fecha y el usuario que realizó el cambio.
 */
@Entity
@Table(name = "historial_precios")
@Data
public class HistorialPrecio {

    /**
     * Identificador único del historial de precios.
     * Generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Relación con la entidad Producto.
     * Indica a qué producto pertenece este registro de historial.
     */
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    /**
     * Precio registrado en este historial.
     */
    @Column(name = "precio")
    private BigDecimal precio;

    /**
     * Fecha en la que se creó este registro de historial.
     * Se asigna automáticamente antes de persistir el registro.
     */
    @Column(name = "fecha_creacion")
    private LocalDateTime fecha;

    /**
     * Relación con la entidad Usuario.
     * Indica el usuario que realizó el cambio en el precio.
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    /**
     * Método que se ejecuta automáticamente antes de persistir un registro.
     * Asigna la fecha de creación actual al campo `fecha`.
     */
    @PrePersist
    protected void onCreate() {
        this.fecha = LocalDateTime.now();
    }
}


