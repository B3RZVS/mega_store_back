package com.tpi_pais.mega_store.products.repository;

import com.tpi_pais.mega_store.products.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que extiende JpaRepository para realizar operaciones CRUD sobre la entidad {@link Producto}.
 * Esta interfaz proporciona métodos adicionales para realizar búsquedas de productos activos o eliminados
 * según condiciones específicas como el nombre, el ID y la fecha de eliminación.
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    /**
     * Lista todos los productos que no han sido eliminados (fechaEliminacion es null).
     * Los resultados se ordenan por ID de forma ascendente.
     *
     * @return Lista de objetos Producto no eliminados, ordenados por ID ascendente.
     */
    List<Producto> findByFechaEliminacionIsNullOrderByIdAsc();

    /**
     * Encuentra un producto activo por su ID, es decir, que no ha sido eliminado.
     *
     * @param id ID del producto.
     * @return Un Optional que contiene el producto si se encuentra y no está eliminado,
     *         o está vacío si no se encuentra o está eliminado.
     */
    Optional<Producto> findByIdAndFechaEliminacionIsNull(Integer id);

    /**
     * Encuentra un producto por su ID, pero solo si ha sido eliminado (fechaEliminacion no es null).
     *
     * @param id ID del producto.
     * @return Un Optional que contiene el producto si se encuentra y está eliminado,
     *         o está vacío si no se encuentra o no está eliminado.
     */
    Optional<Producto> findByIdAndFechaEliminacionIsNotNull(Integer id);

    /**
     * Encuentra un producto activo por su nombre, es decir, que no ha sido eliminado.
     *
     * @param nombre Nombre del producto.
     * @return Un Optional que contiene el producto si se encuentra y no está eliminado,
     *         o está vacío si no se encuentra o está eliminado.
     */
    Optional<Producto> findByNombreAndFechaEliminacionIsNull(String nombre);

    /**
     * Encuentra un producto por su nombre, sin importar si está eliminado o no.
     *
     * @param nombre Nombre del producto.
     * @return Un Optional que contiene el producto si se encuentra, o está vacío si no se encuentra.
     */
    Optional<Producto> findByNombre(String nombre);

    /**
     * Encuentra un producto por su nombre, pero solo si ha sido eliminado (fechaEliminacion no es null).
     *
     * @param nombre Nombre del producto.
     * @return Un Optional que contiene el producto si se encuentra y está eliminado,
     *         o está vacío si no se encuentra o no está eliminado.
     */
    Optional<Producto> findByNombreAndFechaEliminacionIsNotNull(String nombre);
}
