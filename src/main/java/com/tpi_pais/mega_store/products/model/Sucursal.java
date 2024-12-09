package com.tpi_pais.mega_store.products.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;
import java.time.LocalDateTime;

/**
 * Representa una sucursal dentro del sistema Mega Store.
 * Incluye detalles como el nombre de la sucursal y la fecha de eliminación (para gestión lógica de eliminación).
 */
@Entity
@Table(name = "sucursales")
@Data
@ToString
public class Sucursal {

    /**
     * Identificador único de la sucursal.
     * Generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre de la sucursal.
     * Debe tener entre 1 y 100 caracteres.
     */
    @Size(min = 1, max = 100, message = "El nombre de la sucursal debe tener entre 1 y 100 caracteres")
    @NotNull(message = "El nombre de la sucursal es obligatorio")
    @Column(name = "nombre")
    private String nombre;

    /**
     * Fecha de eliminación de la sucursal.
     * Se establece cuando la sucursal es eliminada (de forma lógica).
     */
    @Column(name = "fecha_eliminacion")
    private LocalDateTime fechaEliminacion;

    /**
     * Método para eliminar (de forma lógica) la sucursal.
     * Establece la fecha de eliminación a la hora actual.
     */
    public void eliminar() {
        this.fechaEliminacion = LocalDateTime.now();
    }

    /**
     * Método para recuperar una sucursal eliminada (restaurar su estado).
     * Elimina la fecha de eliminación, lo que indica que la sucursal ya no está eliminada.
     */
    public void recuperar() {
        this.fechaEliminacion = null;
    }

    /**
     * Verifica si la sucursal ha sido eliminada (de forma lógica).
     *
     * @return true si la sucursal está eliminada, false si no.
     */
    public boolean esEliminado() {
        return this.fechaEliminacion != null;
    }
}
