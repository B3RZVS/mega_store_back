package com.tpi_pais.mega_store.products.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Clase que representa la entidad Categoría en la base de datos.
 * Gestiona la información de las categorías de productos en el sistema.
 */
@Entity
@Table(name = "categorias")
@Data
@ToString
public class Categoria {

    /**
     * Identificador único de la categoría.
     * Generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre de la categoría.
     * Es obligatorio y debe contener entre 1 y 100 caracteres.
     */
    @Size(min = 1, max = 100, message = "El nombre de la categoría debe tener entre 1 y 100 caracteres")
    @NotNull
    @Column(name = "nombre")
    private String nombre;

    /**
     * Fecha de eliminación lógica de la categoría.
     * Si es null, la categoría está activa; de lo contrario, está eliminada.
     */
    @Column(name = "fecha_eliminacion")
    private LocalDateTime fechaEliminacion;

    /**
     * Marca la categoría como eliminada, asignando la fecha actual al campo `fechaEliminacion`.
     */
    public void eliminar() {
        this.fechaEliminacion = LocalDateTime.now();
    }

    /**
     * Recupera una categoría eliminada, eliminando el valor del campo `fechaEliminacion`.
     */
    public void recuperar() {
        this.setFechaEliminacion(null);
    }

    /**
     * Verifica si la categoría está eliminada.
     *
     * @return true si la categoría está eliminada, false en caso contrario.
     */
    public boolean esEliminado() {
        return this.fechaEliminacion != null;
    }
}

