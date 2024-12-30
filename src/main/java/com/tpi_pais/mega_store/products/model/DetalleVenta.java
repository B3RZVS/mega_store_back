package com.tpi_pais.mega_store.products.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Representa un detalle de una venta en el sistema.
 * Contiene información sobre el producto, la venta asociada,
 * la cantidad, el precio unitario, el subtotal y las marcas de eliminación lógica.
 */
@Entity
@Table(name = "detalles_ventas")
@Data
public class DetalleVenta {

    /**
     * Identificador único del detalle de la venta.
     * Generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Producto asociado al detalle de venta.
     */
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    /**
     * Venta asociada al detalle de venta.
     */
    @ManyToOne
    @JoinColumn(name = "venta_id")
    private Venta venta;

    /**
     * Cantidad de productos vendidos en este detalle de venta.
     */
    @Column(name = "cantidad")
    private Integer cantidad;

    /**
     * Precio unitario del producto en la venta.
     */
    @Column(name = "precio_unitario")
    private Double precioUnitario;

    /**
     * Subtotal calculado como cantidad * precioUnitario.
     */
    @Column(name = "subtotal")
    private Double subtotal;

    /**
     * Fecha y hora en la que se eliminó lógicamente el detalle de venta.
     * Se utiliza para realizar un borrado lógico, sin eliminar físicamente los datos.
     */
    @Column(name = "fecha_eliminacion")
    private LocalDateTime fechaEliminacion;
}
