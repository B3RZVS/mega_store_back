package com.tpi_pais.mega_store.products.repository;

import com.tpi_pais.mega_store.products.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MarcaRepository extends JpaRepository<Marca,Integer> {

    List<Marca> findByFechaEliminacionIsNullOrderByIdAsc();

    Optional<Marca> findByNombre(String nombre);
}
