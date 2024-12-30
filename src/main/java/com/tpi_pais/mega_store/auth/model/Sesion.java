package com.tpi_pais.mega_store.auth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Clase que representa la entidad Sesion en la base de datos.
 * Gestiona la información de las sesiones de los usuarios en el sistema.
 */
@Entity
@Table(name = "sesiones")
@Data
@ToString
public class Sesion {

    /**
     * Identificador único de la sesión.
     * Generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Token único asociado a la sesión.
     * No puede ser nulo.
     */
    @Column(name = "token")
    @NotNull
    private String token;

    /**
     * Fecha de eliminación lógica de la sesión.
     * Si es null, la sesión está activa; de lo contrario, está eliminada.
     */
    @Column(name = "fecha_eliminacion")
    private LocalDateTime fechaEliminacion;

    /**
     * Fecha de creación de la sesión.
     * Se asigna automáticamente al crear la sesión.
     */
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    /**
     * Usuario asociado a la sesión.
     * Este campo no puede ser nulo y se define como una clave foránea.
     */
    @ManyToOne
    @NotNull
    @JoinColumn(name = "usuario_id") // Define la relación con la tabla Usuario
    private Usuario usuario;

    /**
     * Método ejecutado antes de persistir la sesión en la base de datos.
     * Inicializa la fecha de creación y genera un token único.
     */
    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        this.token = generarUUID();
    }

    /**
     * Verifica si la sesión está eliminada.
     *
     * @return true si la sesión está eliminada, false en caso contrario.
     */
    public boolean esEliminado() {
        return this.fechaEliminacion != null;
    }

    /**
     * Marca la sesión como eliminada asignándole la fecha actual.
     */
    public void eliminar() {
        this.fechaEliminacion = LocalDateTime.now();
    }

    /**
     * Genera un token único utilizando UUID.
     *
     * @return Un token único como cadena.
     */
    public static String generarUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * Verifica si la sesión está activa.
     * Una sesión es considerada activa si tiene menos de 8 horas desde su creación.
     *
     * @return true si la sesión está activa, false en caso contrario.
     */
    public boolean esActivo() {
        Duration duration = Duration.between(this.getFechaCreacion(), LocalDateTime.now());
        long horasTranscurridas = duration.toHours();
        return horasTranscurridas < 8;
    }
}

