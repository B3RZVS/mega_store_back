package com.tpi_pais.mega_store.products.model;

import com.tpi_pais.mega_store.auth.model.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Representa una venta en el sistema.
 * Contiene información sobre la fecha de la venta, el usuario asociado,
 * el número y total de la venta, y las marcas de eliminación lógica.
 */
@Entity
@Table(name = "ventas")
@Data
public class Venta {

    /**
     * Identificador único de la venta.
     * Generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Fecha y hora en la que se registró la venta.
     */
    @Column(name = "fecha_venta")
    private LocalDateTime fechaCreacion;

    /**
     * Relación con la entidad Usuario.
     * Representa al usuario que realizó la venta.
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    /**
     * Total monetario de la venta.
     */
    @Column(name = "total_venta")
    private Double totalVenta;

    /**
     * Fecha y hora en la que se eliminó lógicamente la venta.
     * Se utiliza para realizar un borrado lógico, sin eliminar físicamente los datos.
     */
    @Column(name = "fecha_eliminacion")
    private LocalDateTime fechaEliminacion;

    /**
     * Método de ciclo de vida de JPA que se ejecuta antes de persistir el objeto en la base de datos.
     * Asigna la fecha de creación al momento actual.
     */
    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
    }

    /**
     * Marca la venta como eliminada lógicamente asignando la fecha de eliminación actual.
     */
    public void eliminar() {
        this.fechaEliminacion = LocalDateTime.now();
    }

    /**
     * Recupera la venta eliminada lógicamente, eliminando la marca de eliminación lógica.
     */
    public void recuperar() {
        this.setFechaEliminacion(null);
    }

    /**
     * Verifica si la venta está eliminada lógicamente.
     *
     * @return {@code true} si la venta está eliminada lógicamente, {@code false} en caso contrario.
     */
    public boolean esEliminado() {
        return this.fechaEliminacion != null;
    }
}
