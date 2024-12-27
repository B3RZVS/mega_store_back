package com.tpi_pais.mega_store.products.repository;

import com.tpi_pais.mega_store.products.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz VentaRepository
 * Esta interfaz define los métodos necesarios para realizar operaciones CRUD
 * sobre la entidad Venta en la base de datos. Extiende JpaRepository, lo que
 * proporciona métodos básicos para la gestión de la persistencia, como guardar,
 * actualizar, eliminar y buscar.
 * Métodos personalizados incluyen búsquedas específicas relacionadas con el
 * estado lógico de eliminaciones (soft delete) y consultas específicas por
 * usuario y número de venta.
 */
public interface VentaRepository extends JpaRepository<Venta, Integer> {

    /**
     * Busca una venta activa (no eliminada) por su ID.
     *
     * @param id ID de la venta a buscar.
     * @return Un Optional que contiene la venta si se encuentra y no ha sido eliminada.
     */
    Optional<Venta> findByIdAndFechaEliminacionIsNull(Integer id);

    /**
     * Busca una venta eliminada lógicamente por su ID.
     *
     * @param id ID de la venta a buscar.
     * @return Un Optional que contiene la venta si se encuentra y ha sido eliminada.
     */
    Optional<Venta> findByIdAndFechaEliminacionIsNotNull(Integer id);

    /**
     * Recupera una lista de todas las ventas activas (no eliminadas),
     * ordenadas por ID en orden ascendente.
     *
     * @return Lista de ventas activas ordenadas por ID.
     */
    List<Venta> findByFechaEliminacionIsNullOrderByIdAsc();

    /**
     * Recupera una lista de todas las ventas eliminadas lógicamente,
     * ordenadas por ID en orden ascendente.
     *
     * @return Lista de ventas eliminadas ordenadas por ID.
     */
    List<Venta> findByFechaEliminacionIsNotNullOrderByIdAsc();

    /**
     * Recupera una lista de todas las ventas activas (no eliminadas)
     * asociadas a un usuario específico, identificado por su ID.
     *
     * @param id ID del usuario propietario de las ventas.
     * @return Lista de ventas activas asociadas al usuario.
     */
    List<Venta> findByFechaEliminacionIsNullAndUsuarioId(Integer id);

}
