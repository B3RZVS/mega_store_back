package com.tpi_pais.mega_store.auth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Clase que representa la entidad Rol en la base de datos.
 * Define los roles disponibles en el sistema y su estado.
 */
@Entity
@Table(name = "roles")
@Data
@ToString
public class Rol {

    /**
     * Identificador único del rol.
     * Generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre del rol.
     * No puede ser nulo y debe tener entre 1 y 100 caracteres.
     */
    @Size(min = 1, max = 100, message = "El nombre del rol debe tener menos de 100 caracteres")
    @NotNull
    @Column(name = "nombre")
    private String nombre;

    /**
     * Fecha de eliminación lógica del rol.
     * Si es null, el rol está activo; de lo contrario, está eliminado.
     */
    @Column(name = "fecha_eliminacion")
    private LocalDateTime fechaEliminacion;

    /**
     * Marca el rol como eliminado asignándole la fecha actual.
     */
    public void eliminar() {
        this.fechaEliminacion = LocalDateTime.now();
    }

    /**
     * Recupera el rol eliminado, limpiando el campo de fecha de eliminación.
     */
    public void recuperar() {
        this.setFechaEliminacion(null);
    }

    /**
     * Verifica si el rol está eliminado.
     *
     * @return true si el rol está eliminado, false en caso contrario.
     */
    public boolean esEliminado() {
        return this.fechaEliminacion != null;
    }
}


