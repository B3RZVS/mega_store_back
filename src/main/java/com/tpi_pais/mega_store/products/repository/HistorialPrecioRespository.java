package com.tpi_pais.mega_store.products.repository;


import com.tpi_pais.mega_store.products.model.HistorialPrecio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HistorialPrecioRespository extends JpaRepository<HistorialPrecio, Integer> {

    // Listar historiales por producto (más recientes primero)
    List<HistorialPrecio> findByProductoIdOrderByFechaDesc(Integer productoId);

    // Listar historiales por usuario (más recientes primero)
    List<HistorialPrecio> findByUsuarioIdOrderByFechaDesc(Integer usuarioId);

    // Obtener el historial de precio actual (más reciente) de un producto
    Optional<HistorialPrecio> findFirstByProductoIdOrderByFechaDesc(Integer productoId);
}
