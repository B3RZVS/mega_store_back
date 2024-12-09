package com.tpi_pais.mega_store.products.repository;

import com.tpi_pais.mega_store.products.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz para realizar operaciones CRUD sobre el repositorio de {@link Categoria}.
 * Proporciona métodos para recuperar categorías que no han sido eliminadas o aquellas que sí lo han sido.
 */
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    /**
     * Recupera una lista de categorías que no han sido eliminadas, ordenadas por ID de forma ascendente.
     *
     * @return Lista de categorías que no están eliminadas, ordenadas por ID.
     */
    List<Categoria> findByFechaEliminacionIsNullOrderByIdAsc();

    /**
     * Recupera una categoría por su nombre, solo si no ha sido eliminada.
     *
     * @param nombre Nombre de la categoría que se busca.
     * @return Un Optional que contiene la categoría con el nombre especificado, o vacío si no existe.
     */
    Optional<Categoria> findByNombre(String nombre);

    /**
     * Recupera una categoría por su ID, solo si no ha sido eliminada.
     *
     * @param id ID de la categoría a buscar.
     * @return Un Optional que contiene la categoría con el ID especificado, o vacío si no se encuentra o ha sido eliminada.
     */
    Optional<Categoria> findByIdAndFechaEliminacionIsNull(Integer id);

    /**
     * Recupera una categoría por su ID, solo si ha sido eliminada.
     *
     * @param id ID de la categoría a buscar.
     * @return Un Optional que contiene la categoría con el ID especificado, o vacío si no se encuentra o no ha sido eliminada.
     */
    Optional<Categoria> findByIdAndFechaEliminacionIsNotNull(Integer id);
}
