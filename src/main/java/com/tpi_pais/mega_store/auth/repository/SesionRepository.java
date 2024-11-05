package com.tpi_pais.mega_store.auth.repository;

import com.tpi_pais.mega_store.auth.model.Sesion;
import com.tpi_pais.mega_store.auth.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SesionRepository extends JpaRepository<Sesion, Integer> {
    Optional<Sesion> findByFechaEliminacionIsNullAndToken(String token);

    Optional<Sesion> findByUsuarioAndFechaEliminacionIsNull(Usuario usuario);

}
