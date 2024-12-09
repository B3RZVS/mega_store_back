package com.tpi_pais.mega_store.products.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;
import java.time.LocalDateTime;

/**
 * Representa un talle dentro del sistema Mega Store.
 * Incluye detalles como el nombre del talle y la fecha de eliminación (para gestión lógica de eliminación).
 */
@Entity
@Table(name = "talles")
@Data
@ToString
public class Talle {

    /**
     * Identificador único del talle.
     * Generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre del talle.
     * Debe tener entre 1 y 100 caracteres.
     */
    @Size(min = 1, max = 100, message = "El nombre del talle debe tener entre 1 y 100 caracteres")
    @NotNull(message = "El nombre del talle es obligatorio")
    @Column(name = "nombre")
    private String nombre;

    /**
     * Fecha de eliminación del talle.
     * Se establece cuando el talle es eliminado (de forma lógica).
     */
    @Column(name = "fecha_eliminacion")
    private LocalDateTime fechaEliminacion;

    /**
     * Método para eliminar (de forma lógica) el talle.
     * Establece la fecha de eliminación a la hora actual.
     */
    public void eliminar() {
        this.fechaEliminacion = LocalDateTime.now();
    }

    /**
     * Método para recuperar un talle eliminado (restaurar su estado).
     * Elimina la fecha de eliminación, lo que indica que el talle ya no está eliminado.
     */
    public void recuperar() {
        this.fechaEliminacion = null;
    }

    /**
     * Verifica si el talle ha sido eliminado (de forma lógica).
     *
     * @return true si el talle está eliminado, false si no.
     */
    public boolean esEliminado() {
        return this.fechaEliminacion != null;
    }
}
