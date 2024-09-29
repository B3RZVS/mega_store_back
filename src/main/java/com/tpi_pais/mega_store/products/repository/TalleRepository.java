package com.tpi_pais.mega_store.products.repository;

import com.tpi_pais.mega_store.products.model.Talle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TalleRepository extends JpaRepository<Talle,Integer> {

    List<Talle> findByFechaEliminacionIsNullOrderByIdAsc();

    Optional<Talle> findByNombre(String nombre);
}
