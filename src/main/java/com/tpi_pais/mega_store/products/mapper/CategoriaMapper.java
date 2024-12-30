package com.tpi_pais.mega_store.products.mapper;

import com.tpi_pais.mega_store.products.dto.CategoriaDTO;
import com.tpi_pais.mega_store.products.model.Categoria;
import org.springframework.stereotype.Component;

/**
 * Clase Mapper para la conversión entre el modelo de datos Categoria y el DTO CategoriaDTO.
 * Facilita la transformación de los datos entre la capa de persistencia y la capa de presentación.
 */
@Component
public class CategoriaMapper {

    /**
     * Constructor privado para evitar la creación de instancias de la clase.
     */
    private CategoriaMapper() {}

    /**
     * Convierte un objeto de tipo Categoria en un objeto de tipo CategoriaDTO.
     *
     * @param model El objeto Categoria a convertir.
     * @return Un objeto CategoriaDTO con los mismos datos que el objeto Categoria.
     */
    public static CategoriaDTO toDTO(Categoria model) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(model.getId()); // Asigna el ID de la categoría.
        dto.setNombre(model.getNombre()); // Asigna el nombre de la categoría.
        dto.setFechaEliminacion(model.getFechaEliminacion()); // Asigna la fecha de eliminación.
        return dto; // Devuelve el DTO creado.
    }

    /**
     * Convierte un objeto de tipo CategoriaDTO en un objeto de tipo Categoria.
     *
     * @param dto El objeto CategoriaDTO a convertir.
     * @return Un objeto Categoria con los mismos datos que el objeto CategoriaDTO.
     */
    public static Categoria toEntity(CategoriaDTO dto) {
        Categoria model = new Categoria();
        model.setId(dto.getId()); // Asigna el ID de la categoría desde el DTO.
        model.setNombre(dto.getNombre()); // Asigna el nombre de la categoría desde el DTO.
        model.setFechaEliminacion(dto.getFechaEliminacion()); // Asigna la fecha de eliminación desde el DTO.
        return model; // Devuelve el modelo convertido.
    }
}
