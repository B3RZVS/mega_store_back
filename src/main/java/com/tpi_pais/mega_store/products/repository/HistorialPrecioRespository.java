package com.tpi_pais.mega_store.products.repository;

import com.tpi_pais.mega_store.products.model.HistorialPrecio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz para realizar operaciones CRUD sobre el repositorio de {@link HistorialPrecio}.
 * Proporciona métodos para recuperar historiales de precios, tanto por producto como por usuario.
 */
public interface HistorialPrecioRespository extends JpaRepository<HistorialPrecio, Integer> {

    /**
     * Recupera una lista de historiales de precios de un producto, ordenados por fecha (más recientes primero).
     *
     * @param productoId ID del producto para el cual se buscan los historiales de precio.
     * @return Lista de historiales de precios de un producto, ordenados por fecha.
     */
    List<HistorialPrecio> findByProductoIdOrderByFechaDesc(Integer productoId);

    /**
     * Recupera una lista de historiales de precios de un usuario, ordenados por fecha (más recientes primero).
     *
     * @param usuarioId ID del usuario para el cual se buscan los historiales de precio.
     * @return Lista de historiales de precios de un usuario, ordenados por fecha.
     */
    List<HistorialPrecio> findByUsuarioIdOrderByFechaDesc(Integer usuarioId);

    /**
     * Obtiene el historial de precio más reciente de un producto.
     *
     * @param productoId ID del producto para el cual se obtiene el historial de precio más reciente.
     * @return Un Optional que contiene el historial de precio más reciente, o vacío si no se encuentra.
     */
    Optional<HistorialPrecio> findFirstByProductoIdOrderByFechaDesc(Integer productoId);
}
