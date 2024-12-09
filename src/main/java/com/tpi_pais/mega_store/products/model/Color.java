package com.tpi_pais.mega_store.products.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Clase que representa la entidad Color en la base de datos.
 * Gestiona la información de los colores asociados a productos.
 */
@Entity
@Table(name = "colores")
@Data
@ToString
public class Color {

    /**
     * Identificador único del color.
     * Generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre del color.
     * Es obligatorio y debe contener entre 1 y 100 caracteres.
     */
    @Size(min = 1, max = 100, message = "El nombre del color debe tener entre 1 y 100 caracteres")
    @NotNull
    @Column(name = "nombre")
    private String nombre;

    /**
     * Fecha de eliminación lógica del color.
     * Si es null, el color está activo; de lo contrario, está eliminado.
     */
    @Column(name = "fecha_eliminacion")
    private LocalDateTime fechaEliminacion;

    /**
     * Marca el color como eliminado, asignando la fecha actual al campo `fechaEliminacion`.
     */
    public void eliminar() {
        this.fechaEliminacion = LocalDateTime.now();
    }

    /**
     * Recupera un color eliminado, eliminando el valor del campo `fechaEliminacion`.
     */
    public void recuperar() {
        this.fechaEliminacion = null;
    }

    /**
     * Verifica si el color está eliminado.
     *
     * @return true si el color está eliminado, false en caso contrario.
     */
    public boolean esEliminado() {
        return this.fechaEliminacion != null;
    }
}
