package com.tpi_pais.mega_store.auth.repository;

import com.tpi_pais.mega_store.auth.model.Rol;
import com.tpi_pais.mega_store.products.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol,Integer> {

    List<Rol> findByFechaEliminacionIsNullOrderByIdAsc();

    Optional<Rol> findByNombre(String nombre);

    Optional<Rol> findByNombreAndFechaEliminacionIsNull(String nombre);

    Optional<Rol> findByNombreAndFechaEliminacionIsNotNull(String nombre);

    Optional<Rol> findByIdAndFechaEliminacionIsNull(Integer id);

    Optional<Rol> findByIdAndFechaEliminacionIsNotNull(Integer id);
}

