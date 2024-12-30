package com.tpi_pais.mega_store.products.mapper;

import com.tpi_pais.mega_store.products.dto.TalleDTO;
import com.tpi_pais.mega_store.products.model.Talle;
import org.springframework.stereotype.Component;

/**
 * Clase Mapper para la conversión entre el modelo de datos Talle y el DTO TalleDTO.
 * Facilita la transformación de los datos entre la capa de persistencia y la capa de presentación.
 */
@Component
public class TalleMapper {

    /**
     * Constructor de la clase. No se necesita inicializar ningún componente adicional.
     */
    private TalleMapper() {}

    /**
     * Convierte un objeto de tipo Talle en un objeto de tipo TalleDTO.
     *
     * @param model El objeto Talle a convertir.
     * @return Un objeto TalleDTO con los mismos datos que el objeto Talle.
     */
    public static TalleDTO toDTO(Talle model) {
        TalleDTO dto = new TalleDTO();
        dto.setId(model.getId()); // Asigna el ID del talle.
        dto.setNombre(model.getNombre()); // Asigna el nombre del talle.
        dto.setFechaEliminacion(model.getFechaEliminacion()); // Asigna la fecha de eliminación (si existe).
        return dto; // Devuelve el DTO con los datos asignados.
    }

    /**
     * Convierte un objeto de tipo TalleDTO en un objeto de tipo Talle.
     *
     * @param dto El objeto TalleDTO a convertir.
     * @return Un objeto Talle con los mismos datos que el objeto TalleDTO.
     */
    public static Talle toEntity(TalleDTO dto) {
        Talle model = new Talle();
        model.setId(dto.getId()); // Asigna el ID del DTO al modelo.
        model.setNombre(dto.getNombre()); // Asigna el nombre del DTO al modelo.
        model.setFechaEliminacion(dto.getFechaEliminacion()); // Asigna la fecha de eliminación del DTO al modelo.
        return model; // Devuelve el modelo con los datos asignados.
    }
}
