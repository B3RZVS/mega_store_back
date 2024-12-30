package com.tpi_pais.mega_store.auth.repository;

import com.tpi_pais.mega_store.auth.model.Sesion;
import com.tpi_pais.mega_store.auth.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio de {@link Sesion} para realizar operaciones CRUD sobre las sesiones de usuario.
 * Proporciona métodos para recuperar sesiones activas por token o usuario.
 */
public interface SesionRepository extends JpaRepository<Sesion, Integer> {

    /**
     * Recupera una sesión activa por su token.
     *
     * @param token El token de la sesión que se busca.
     * @return Un Optional que contiene la sesión con el token especificado, o vacío si no existe o ha sido eliminada.
     */
    Optional<Sesion> findByFechaEliminacionIsNullAndToken(String token);

    /**
     * Recupera una sesión activa por el usuario asociado.
     *
     * @param usuario El usuario cuya sesión se busca.
     * @return Un Optional que contiene la sesión asociada al usuario, o vacío si no existe o ha sido eliminada.
     */
    Optional<Sesion> findByUsuarioAndFechaEliminacionIsNull(Usuario usuario);

}
