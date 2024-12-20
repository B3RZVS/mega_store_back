package com.tpi_pais.mega_store.auth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Table(name = "sesiones")
@Data
@ToString
public class Sesion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "token")
    @NotNull
    private String token;

    @Column(name = "fecha_eliminacion")
    private LocalDateTime fechaEliminacion;

    @Column (name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "usuario_id")  // Solo usa @JoinColumn para la FK
    private Usuario usuario;


    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        this.token = generarUUID();
    }
    public boolean esEliminado() { return this.fechaEliminacion != null; }

    public void eliminar () {
        this.fechaEliminacion = LocalDateTime.now();
    }

    public static String generarUUID() {
        // Generar un UUID
        UUID uuid = UUID.randomUUID();
        // Devolverlo como una cadena
        return uuid.toString();
    }

    public boolean esActivo() {
        Duration duration = Duration.between(this.getFechaCreacion(), LocalDateTime.now());
        long horasTranscurridas = duration.toHours();
        return horasTranscurridas < 8;
    }
}
