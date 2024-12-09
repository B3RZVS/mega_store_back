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
import java.util.UUID;

/**
 * Clase que representa la entidad Usuario en la base de datos.
 * Gestiona la información de los usuarios del sistema, incluyendo datos personales,
 * rol asignado, y estado de verificación.
 */
@Entity
@Table(name = "usuarios")
@Data
@ToString
public class Usuario {

    /**
     * Identificador único del usuario.
     * Generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre del usuario.
     * No puede ser nulo y debe tener entre 1 y 100 caracteres.
     */
    @Size(min = 1, max = 100, message = "El nombre del usuario debe tener menos de 100 caracteres")
    @NotNull
    @Column(name = "nombre")
    private String nombre;

    /**
     * Correo electrónico del usuario.
     * No puede ser nulo y debe tener entre 1 y 100 caracteres.
     */
    @Size(min = 1, max = 100, message = "El email del usuario debe tener menos de 100 caracteres")
    @NotNull
    @Column(name = "email")
    private String email;

    /**
     * Teléfono del usuario.
     * Puede contener hasta 100 caracteres.
     */
    @Size(min = 1, max = 100, message = "El teléfono del usuario debe tener menos de 100 caracteres")
    @Column(name = "telefono")
    private String telefono;

    /**
     * Dirección de envío asociada al usuario.
     * Puede contener hasta 100 caracteres.
     */
    @Size(min = 1, max = 100, message = "La dirección de envío debe tener menos de 100 caracteres")
    @Column(name = "direccion_envio")
    private String direccionEnvio;

    /**
     * Fecha de creación del usuario.
     * Usada para validar la vigencia del código de verificación.
     */
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    /**
     * Código de verificación generado tras el registro.
     * Debe tener hasta 7 caracteres.
     */
    @Size(min = 1, max = 7, message = "El código de verificación debe tener menos de 7 caracteres")
    @Column(name = "codigo_verificacion")
    private String codigoVerificacion;

    /**
     * Indica si el usuario ha sido verificado exitosamente.
     */
    @Column(name = "verificado")
    private Boolean verificado;

    /**
     * Contraseña del usuario, almacenada de forma encriptada.
     * No puede ser nula.
     */
    @NotNull
    @Column(name = "password")
    private String password;

    /**
     * Rol asociado al usuario, definido como una relación con la tabla Rol.
     */
    @ManyToOne
    @JoinColumn(name = "rol_id") // Define la relación con la tabla Rol
    private Rol rol;

    /**
     * Fecha de eliminación lógica del usuario.
     * Si es null, el usuario está activo; de lo contrario, está eliminado.
     */
    @Column(name = "fecha_eliminacion")
    private LocalDateTime fechaEliminacion;

    /**
     * Proveedor de encriptación de contraseñas usando BCrypt.
     *
     * @return Instancia de PasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Establece la contraseña del usuario, encriptándola antes de guardarla.
     *
     * @param password Contraseña en texto plano.
     */
    public void setPassword(String password) {
        PasswordEncoder pE = this.passwordEncoder();
        this.password = pE.encode(password);
    }

    /**
     * Verifica si una contraseña en texto plano coincide con la encriptada almacenada.
     *
     * @param password Contraseña en texto plano.
     * @return true si coinciden, false en caso contrario.
     */
    public Boolean checkPassword(String password) {
        PasswordEncoder pE = this.passwordEncoder();
        return pE.matches(password, this.password);
    }

    /**
     * Marca al usuario como eliminado, asignando la fecha actual.
     */
    public void eliminar() {
        this.fechaEliminacion = LocalDateTime.now();
    }

    /**
     * Recupera al usuario eliminado, limpiando el campo de fecha de eliminación.
     */
    public void recuperar() {
        this.setFechaEliminacion(null);
    }

    /**
     * Establece la fecha de creación del usuario como la fecha actual.
     */
    public void setFechaCreacion() {
        this.fechaCreacion = LocalDateTime.now();
    }

    /**
     * Marca al usuario como verificado.
     */
    public void activar() {
        this.verificado = true;
    }

    /**
     * Verifica si el usuario está eliminado.
     *
     * @return true si el usuario está eliminado, false en caso contrario.
     */
    public boolean esEliminado() {
        return this.fechaEliminacion != null;
    }

    /**
     * Genera un código de verificación único para el usuario.
     * Este código tiene una longitud de 6 caracteres.
     */
    public void setCodigoVerificacion() {
        this.codigoVerificacion = UUID.randomUUID().toString().substring(0, 6);
    }
}
