package com.tpi_pais.mega_store.products.model;

import com.tpi_pais.mega_store.auth.model.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "historial_precios")
@Data
@ToString
public class HistorialPrecio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "fecha_creacion")
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @PrePersist
    protected void onCreate() {
        this.fecha = LocalDateTime.now();
    }
}

