package com.tpi_pais.mega_store.auth.repository;

import com.tpi_pais.mega_store.auth.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de {@link Usuario} para realizar operaciones CRUD sobre los usuarios.
 * Proporciona métodos para consultar usuarios por diversos criterios como nombre, email, estado de verificación, etc.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    /**
     * Recupera un usuario por su nombre.
     *
     * @param nombre El nombre del usuario que se busca.
     * @return Un Optional que contiene el usuario con el nombre especificado, o vacío si no existe.
     */
    public Optional<Usuario> findByNombre(String nombre);

    /**
     * Recupera una lista de usuarios que no han sido eliminados y están verificados, ordenados por su ID.
     *
     * @return Una lista de usuarios verificados y no eliminados, ordenados por su ID en orden ascendente.
     */
    public List<Usuario> findByFechaEliminacionIsNullAndVerificadoTrueOrderByIdAsc();

    /**
     * Recupera un usuario por su email.
     *
     * @param email El correo electrónico del usuario que se busca.
     * @return Un Optional que contiene el usuario con el email especificado, o vacío si no existe.
     */
    public Optional<Usuario> findByEmail(String email);

    /**
     * Recupera un usuario por su ID.
     *
     * @param id El ID del usuario que se busca.
     * @return Un Optional que contiene el usuario con el ID especificado, o vacío si no existe.
     */
    public Optional<Usuario> findById(Long id);

    /**
     * Recupera un usuario verificado y no eliminado por su email.
     *
     * @param email El correo electrónico del usuario que se busca.
     * @return Un Optional que contiene el usuario verificado y no eliminado con el email especificado, o vacío si no existe.
     */
    public Optional<Usuario> findByFechaEliminacionIsNullAndVerificadoTrueAndEmail(String email);

    /**
     * Recupera un usuario verificado y no eliminado por su ID.
     *
     * @param id El ID del usuario que se busca.
     * @return Un Optional que contiene el usuario verificado y no eliminado con el ID especificado, o vacío si no existe.
     */
    public Optional<Usuario> findByFechaEliminacionIsNullAndVerificadoTrueAndId(Integer id);
}
