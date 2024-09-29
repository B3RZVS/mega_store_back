package com.tpi_pais.mega_store.products.repository;

import com.tpi_pais.mega_store.products.model.Categoria;
import com.tpi_pais.mega_store.products.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SucursalRepository extends JpaRepository <Sucursal,Integer> {

    List<Sucursal> findByFechaEliminacionIsNullOrderByIdAsc();

    Optional<Sucursal> findByNombre(String nombre);
}
