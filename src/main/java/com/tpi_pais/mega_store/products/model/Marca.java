package com.tpi_pais.mega_store.products.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Clase que representa una marca dentro del sistema Mega Store.
 * Contiene información básica sobre la marca, incluyendo su estado de eliminación lógica.
 */
@Entity
@Table(name = "marcas")
@Data
@ToString
public class Marca {

    /**
     * Identificador único de la marca.
     * Generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre de la marca.
     * Debe tener entre 1 y 100 caracteres.
     */
    @Size(min = 1, max = 100, message = "El nombre de la marca debe tener menos de 100 caracteres")
    @NotNull
    @Column(name = "nombre")
    private String nombre;

    /**
     * Fecha en la que la marca fue eliminada lógicamente.
     * Si es `null`, la marca está activa.
     */
    @Column(name = "fecha_eliminacion")
    private LocalDateTime fechaEliminacion;

    /**
     * Marca la entidad como eliminada lógicamente, asignando la fecha de eliminación actual.
     */
    public void eliminar() {
        this.fechaEliminacion = LocalDateTime.now();
    }

    /**
     * Recupera la entidad, eliminando la marca de eliminación lógica.
     */
    public void recuperar() {
        this.setFechaEliminacion(null);
    }

    /**
     * Verifica si la entidad está eliminada lógicamente.
     *
     * @return `true` si la marca está eliminada, `false` en caso contrario.
     */
    public boolean esEliminado() {
        return this.fechaEliminacion != null;
    }
}
