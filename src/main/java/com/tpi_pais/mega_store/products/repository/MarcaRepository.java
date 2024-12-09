package com.tpi_pais.mega_store.products.repository;

import com.tpi_pais.mega_store.products.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz para realizar operaciones CRUD sobre el repositorio de {@link Marca}.
 * Proporciona métodos para recuperar registros de marcas, incluyendo aquellas que están activas o eliminadas.
 */
public interface MarcaRepository extends JpaRepository<Marca, Integer> {

    /**
     * Recupera una lista de marcas que no están eliminadas, ordenadas por ID de manera ascendente.
     *
     * @return Lista de marcas activas, ordenadas por ID.
     */
    List<Marca> findByFechaEliminacionIsNullOrderByIdAsc();

    /**
     * Encuentra una marca por su nombre.
     *
     * @param nombre Nombre de la marca a buscar.
     * @return Un Optional que contiene la marca encontrada, o vacío si no se encuentra.
     */
    Optional<Marca> findByNombre(String nombre);

    /**
     * Encuentra una marca por su ID, solo si no está eliminada.
     *
     * @param id ID de la marca a buscar.
     * @return Un Optional que contiene la marca encontrada, o vacío si no se encuentra.
     */
    Optional<Marca> findByIdAndFechaEliminacionIsNull(Integer id);

    /**
     * Encuentra una marca por su ID, incluyendo aquellas que están eliminadas.
     *
     * @param id ID de la marca a buscar.
     * @return Un Optional que contiene la marca encontrada, o vacío si no se encuentra.
     */
    Optional<Marca> findByIdAndFechaEliminacionIsNotNull(Integer id);
}
