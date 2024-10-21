package com.tpi_pais.mega_store.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tpi_pais.mega_store.products.model.Color;

import java.util.List;
import java.util.Optional;

public interface ColorRepository extends JpaRepository<Color,Integer>{

    List<Color> findByFechaEliminacionIsNullOrderByIdAsc();

    Optional<Color> findByNombre(String nombre);
}
