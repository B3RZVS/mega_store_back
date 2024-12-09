package com.tpi_pais.mega_store.products.mapper;

import com.tpi_pais.mega_store.products.dto.ColorDTO;
import com.tpi_pais.mega_store.products.model.Color;
import org.springframework.stereotype.Component;

/**
 * Clase Mapper para la conversión entre el modelo de datos Color y el DTO ColorDTO.
 * Facilita la transformación de los datos entre la capa de persistencia y la capa de presentación.
 */
@Component
public class ColorMapper {

    /**
     * Constructor privado para evitar la creación de instancias de la clase.
     */
    private ColorMapper() {}

    /**
     * Convierte un objeto de tipo Color en un objeto de tipo ColorDTO.
     *
     * @param model El objeto Color a convertir.
     * @return Un objeto ColorDTO con los mismos datos que el objeto Color.
     */
    public static ColorDTO toDTO(Color model) {
        ColorDTO dto = new ColorDTO();
        dto.setId(model.getId()); // Asigna el ID del color.
        dto.setNombre(model.getNombre()); // Asigna el nombre del color.
        dto.setFechaEliminacion(model.getFechaEliminacion()); // Asigna la fecha de eliminación.
        return dto; // Devuelve el DTO creado.
    }

    /**
     * Convierte un objeto de tipo ColorDTO en un objeto de tipo Color.
     *
     * @param dto El objeto ColorDTO a convertir.
     * @return Un objeto Color con los mismos datos que el objeto ColorDTO.
     */
    public static Color toEntity(ColorDTO dto) {
        Color model = new Color();
        model.setId(dto.getId()); // Asigna el ID del color desde el DTO.
        model.setNombre(dto.getNombre()); // Asigna el nombre del color desde el DTO.
        model.setFechaEliminacion(dto.getFechaEliminacion()); // Asigna la fecha de eliminación desde el DTO.
        return model; // Devuelve el modelo convertido.
    }
}
