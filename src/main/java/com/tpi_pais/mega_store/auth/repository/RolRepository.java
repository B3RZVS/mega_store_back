package com.tpi_pais.mega_store.auth.repository;

import com.tpi_pais.mega_store.auth.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de {@link Rol} para realizar operaciones CRUD sobre los roles de usuario.
 * Proporciona métodos para recuperar roles activos o eliminados.
 */
public interface RolRepository extends JpaRepository<Rol, Integer> {

    /**
     * Recupera una lista de roles que no han sido eliminados, ordenados por ID de forma ascendente.
     *
     * @return Lista de roles que no están eliminados, ordenados por ID.
     */
    List<Rol> findByFechaEliminacionIsNullOrderByIdAsc();

    /**
     * Recupera un rol por su nombre, independientemente de si está eliminado o no.
     *
     * @param nombre Nombre del rol a buscar.
     * @return Un Optional que contiene el rol con el nombre especificado, o vacío si no existe.
     */
    Optional<Rol> findByNombre(String nombre);

    /**
     * Recupera un rol por su nombre, solo si no ha sido eliminado.
     *
     * @param nombre Nombre del rol que se busca.
     * @return Un Optional que contiene el rol con el nombre especificado, o vacío si no existe o ha sido eliminado.
     */
    Optional<Rol> findByNombreAndFechaEliminacionIsNull(String nombre);

    /**
     * Recupera un rol por su nombre, solo si ha sido eliminado.
     *
     * @param nombre Nombre del rol que se busca.
     * @return Un Optional que contiene el rol con el nombre especificado, o vacío si no existe o no ha sido eliminado.
     */
    Optional<Rol> findByNombreAndFechaEliminacionIsNotNull(String nombre);

    /**
     * Recupera un rol por su ID, solo si no ha sido eliminado.
     *
     * @param id ID del rol a buscar.
     * @return Un Optional que contiene el rol con el ID especificado, o vacío si no se encuentra o ha sido eliminado.
     */
    Optional<Rol> findByIdAndFechaEliminacionIsNull(Integer id);

    /**
     * Recupera un rol por su ID, solo si ha sido eliminado.
     *
     * @param id ID del rol a buscar.
     * @return Un Optional que contiene el rol con el ID especificado, o vacío si no se encuentra o no ha sido eliminado.
     */
    Optional<Rol> findByIdAndFechaEliminacionIsNotNull(Integer id);
}
