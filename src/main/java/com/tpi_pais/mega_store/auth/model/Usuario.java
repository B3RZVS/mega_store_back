package com.tpi_pais.mega_store.auth.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Data
@ToString
public class Usuario {
    /*
    * id (Long)
      -  nombre (String, obligatorio, cadena de caracteres)
       - email (String, obligatorio, validado como email único)
       - direccionEnvio (String, cadena alfanumérica)
       - telefono (String, cadena de caracteres)
      -  contrasena (String, obligatorio, encriptada)
        codigoVerificacion (String, generado tras el registro)
      -  fechaEliminacion (Date)
        verificado (Boolean, indica si la cuenta fue verificada con éxito)
        * -fechaCreacion (Date) para controlar que el codigo de verificacion este disponible durante 15 minutos.
    * */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 1, max = 100, message = "El nombre del usuario debe tener menos de 100 caracteres")
    @NotNull
    @Column(name = "nombre")
    private String nombre;

    @Size(min = 1, max = 100, message = "El email del usuario debe tener menos de 100 caracteres")
    @NotNull
    @Column(name = "email")
    private String email;

    @Size(min = 1, max = 100, message = "El email del usuario debe tener menos de 100 caracteres")
    @Column(name = "telefono")
    private String telefono;

    @Size(min = 1, max = 100, message = "La direccion de envio debe tener menos de 100 caracteres")
    @Column(name = "direccion_envio")
    private String direccionEnvio;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Size(min = 1, max = 7, message = "La direccion de envio debe tener menos de 7 caracteres")
    @Column(name = "codigo_verificacion")
    private String codigoVerificacion;

    @Column(name = "verificado")
    private Boolean verificado;

    @NotNull
    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "rol_id")  // Esta es la FK
    private Rol rol;

    @Column(name = "fecha_eliminacion")
    private LocalDateTime fechaEliminacion;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void setPassword(String password) {
        PasswordEncoder pE = this.passwordEncoder();
        this.password = pE.encode(password);
    }

    public Boolean checkPassword(String password) {
        PasswordEncoder pE = this.passwordEncoder();
        return pE.matches(password, this.password);
    }

    public void eliminar() {
        this.fechaEliminacion = LocalDateTime.now();
    }

    public void recuperar() {
        this.setFechaEliminacion(null);
    }

    public void setFechaCreacion() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public void activar () {
        this.verificado = true;
    }

    public boolean esEliminado() { return this.fechaEliminacion != null; }

    public void setCodigoVerificacion() {
        this.codigoVerificacion = UUID.randomUUID().toString().substring(0, 6);
    }
}
