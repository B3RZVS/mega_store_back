package com.tpi_pais.mega_store.products.repository;

import com.tpi_pais.mega_store.products.model.Talle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que extiende JpaRepository para realizar operaciones CRUD sobre la entidad {@link Talle}.
 * Esta interfaz proporciona métodos adicionales para trabajar con la entidad Talle
 * según condiciones específicas, como la búsqueda de talles no eliminados o por nombre.
 */
public interface TalleRepository extends JpaRepository<Talle, Integer> {

    /**
     * Busca todos los talles que no han sido eliminados (fechaEliminacion es null).
     * Los resultados se ordenan por ID de forma ascendente.
     *
     * @return Lista de objetos Talle no eliminados, ordenados por ID ascendente.
     */
    List<Talle> findByFechaEliminacionIsNullOrderByIdAsc();

    /**
     * Busca un talle por su nombre.
     *
     * @param nombre Nombre del talle.
     * @return Un Optional que contiene el talle si se encuentra, o está vacío si no se encuentra.
     */
    Optional<Talle> findByNombre(String nombre);

    /**
     * Busca un talle por su ID solo si no ha sido eliminado (fechaEliminacion es null).
     *
     * @param id ID del talle.
     * @return Un Optional que contiene el talle si se encuentra y no está eliminado,
     *         o está vacío si no se encuentra o está eliminado.
     */
    Optional<Talle> findByIdAndFechaEliminacionIsNull(Integer id);

    /**
     * Busca un talle por su ID solo si ha sido eliminado (fechaEliminacion no es null).
     *
     * @param id ID del talle.
     * @return Un Optional que contiene el talle si se encuentra y está eliminado,
     *         o está vacío si no se encuentra o no está eliminado.
     */
    Optional<Talle> findByIdAndFechaEliminacionIsNotNull(Integer id);
}
