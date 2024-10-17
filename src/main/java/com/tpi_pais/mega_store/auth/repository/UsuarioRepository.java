package com.tpi_pais.mega_store.auth.repository;

import com.tpi_pais.mega_store.auth.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

    public Optional<Usuario> findByNombre(String nombre);

    public List<Usuario> findByFechaEliminacionIsNullAndActivoTrueOrderByIdAsc();

    public Optional<Usuario> findByEmail(String email);

    public Optional<Usuario> findById(Long id);

}
