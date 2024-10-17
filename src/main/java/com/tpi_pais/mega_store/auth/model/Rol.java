package com.tpi_pais.mega_store.auth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
@Entity
@Table(name = "roles")
@Data
@ToString
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 1, max = 100, message = "El nombre de la marca debe tener menos de 100 caracteres")
    @NotNull
    @Column(name = "nombre")
    private String nombre;


    @Column(name = "fecha_eliminacion")
    private LocalDateTime fechaEliminacion;

    public void eliminar() {
        this.fechaEliminacion = LocalDateTime.now();
    }

    public void recuperar() {
        this.setFechaEliminacion(null);
    }

    public boolean esEliminado() { return this.fechaEliminacion != null; }
}



