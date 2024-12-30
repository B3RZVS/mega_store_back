package com.tpi_pais.mega_store.products.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_sucursal")
@Data
public class StockSucursal {

    /**
     * Identificador único del movimiento de stock.
     * Generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Stock disponible en la sucursal.
     */
    @Column(name = "stock")
    private Integer stock;

    /**
     * Sucursal asociada al stock.
     */
    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private Sucursal sucursal;

    /**
     * Producto asociado al stock.
     */
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    /**
     * Fecha de creación del movimiento de stock.
     */
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    /**
     * Metodo de ciclo de vida de JPA que se ejecuta antes de persistir el objeto.
     * Asigna la fecha de creación al momento actual.
     */
    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
    }
}
