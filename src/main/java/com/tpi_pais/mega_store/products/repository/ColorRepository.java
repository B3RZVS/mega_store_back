package com.tpi_pais.mega_store.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tpi_pais.mega_store.products.model.Color;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz para realizar operaciones CRUD sobre el repositorio de {@link Color}.
 * Proporciona métodos para recuperar colores que no han sido eliminados o aquellos que sí lo han sido.
 */
public interface ColorRepository extends JpaRepository<Color, Integer> {

    /**
     * Recupera una lista de colores que no han sido eliminados, ordenados por ID de forma ascendente.
     *
     * @return Lista de colores que no están eliminados, ordenados por ID.
     */
    List<Color> findByFechaEliminacionIsNullOrderByIdAsc();

    /**
     * Recupera un color por su nombre, solo si no ha sido eliminado.
     *
     * @param nombre Nombre del color que se busca.
     * @return Un Optional que contiene el color con el nombre especificado, o vacío si no existe.
     */
    Optional<Color> findByNombre(String nombre);

    /**
     * Recupera un color por su ID, solo si no ha sido eliminado.
     *
     * @param id ID del color a buscar.
     * @return Un Optional que contiene el color con el ID especificado, o vacío si no se encuentra o ha sido eliminado.
     */
    Optional<Color> findByIdAndFechaEliminacionIsNull(Integer id);

    /**
     * Recupera un color por su ID, solo si ha sido eliminado.
     *
     * @param id ID del color a buscar.
     * @return Un Optional que contiene el color con el ID especificado, o vacío si no se encuentra o no ha sido eliminado.
     */
    Optional<Color> findByIdAndFechaEliminacionIsNotNull(Integer id);
}
