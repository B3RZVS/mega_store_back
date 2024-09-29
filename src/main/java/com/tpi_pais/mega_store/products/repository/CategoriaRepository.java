package com.tpi_pais.mega_store.products.repository;

import com.tpi_pais.mega_store.products.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository  <Categoria,Integer>{

    List<Categoria> findByFechaEliminacionIsNullOrderByIdAsc();

    Optional<Categoria> findByNombre(String nombre);
}

