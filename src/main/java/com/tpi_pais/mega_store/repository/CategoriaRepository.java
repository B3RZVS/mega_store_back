package com.tpi_pais.mega_store.repository;

import com.tpi_pais.mega_store.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository  <Categoria,Integer>{

    List<Categoria> findByFechaEliminacionIsNull();

}

