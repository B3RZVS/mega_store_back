package com.tpi_pais.mega_store.products.repository;

import com.tpi_pais.mega_store.products.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    // Lista todos los productos que no están eliminados
    List<Producto> findByFechaEliminacionIsNullOrderByIdAsc();

    // Encuentra un producto activo por ID
    Optional<Producto> findByIdAndFechaEliminacionIsNull(Integer id);

    // Encuentra un producto eliminado por ID
    Optional<Producto> findByIdAndFechaEliminacionIsNotNull(Integer id);

    // Encuentra un producto por nombre, sólo si no está eliminado
    Optional<Producto> findByNombreAndFechaEliminacionIsNull(String nombre);

    // Encuentra un producto por nombre, incluyendo los eliminados
    Optional<Producto> findByNombre(String nombre);

    // Encuentra un producto por nombre, pero sólo si está eliminado
    Optional<Producto> findByNombreAndFechaEliminacionIsNotNull(String nombre);
}
