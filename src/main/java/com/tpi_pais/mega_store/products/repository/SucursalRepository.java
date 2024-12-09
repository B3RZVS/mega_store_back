package com.tpi_pais.mega_store.products.repository;

import com.tpi_pais.mega_store.products.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que extiende JpaRepository para realizar operaciones CRUD sobre la entidad {@link Sucursal}.
 * Esta interfaz proporciona métodos adicionales para trabajar con la entidad Sucursal
 * según condiciones específicas, como la búsqueda de sucursales no eliminadas o por nombre.
 */
public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {

    /**
     * Busca todas las sucursales que no han sido eliminadas (fechaEliminacion es null).
     * Los resultados se ordenan por ID de forma ascendente.
     *
     * @return Lista de objetos Sucursal no eliminadas, ordenadas por ID ascendente.
     */
    List<Sucursal> findByFechaEliminacionIsNullOrderByIdAsc();

    /**
     * Busca una sucursal por su nombre.
     *
     * @param nombre Nombre de la sucursal.
     * @return Un Optional que contiene la sucursal si se encuentra, o está vacío si no se encuentra.
     */
    Optional<Sucursal> findByNombre(String nombre);

    /**
     * Busca una sucursal por su ID solo si no ha sido eliminada (fechaEliminacion es null).
     *
     * @param id ID de la sucursal.
     * @return Un Optional que contiene la sucursal si se encuentra y no está eliminada,
     *         o está vacío si no se encuentra o está eliminada.
     */
    Optional<Sucursal> findByIdAndFechaEliminacionIsNull(Integer id);

    /**
     * Busca una sucursal por su ID solo si ha sido eliminada (fechaEliminacion no es null).
     *
     * @param id ID de la sucursal.
     * @return Un Optional que contiene la sucursal si se encuentra y está eliminada,
     *         o está vacío si no se encuentra o no está eliminada.
     */
    Optional<Sucursal> findByIdAndFechaEliminacionIsNotNull(Integer id);
}
