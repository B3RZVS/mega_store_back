package com.tpi_pais.mega_store.products.mapper;

import com.tpi_pais.mega_store.products.dto.MarcaDTO;
import com.tpi_pais.mega_store.products.model.Marca;
import org.springframework.stereotype.Component;

/**
 * Clase Mapper para la conversión entre el modelo de datos Marca y el DTO MarcaDTO.
 * Facilita la transformación de los datos entre la capa de persistencia y la capa de presentación.
 */
@Component
public class MarcaMapper {

    /**
     * Constructor de la clase. No se necesita inicializar ningún componente adicional.
     */
    private MarcaMapper() {}

    /**
     * Convierte un objeto de tipo Marca en un objeto de tipo MarcaDTO.
     *
     * @param model El objeto Marca a convertir.
     * @return Un objeto MarcaDTO con los mismos datos que el objeto Marca.
     */
    public static MarcaDTO toDTO(Marca model) {
        MarcaDTO dto = new MarcaDTO();
        dto.setId(model.getId()); // Asigna el ID de la marca.
        dto.setNombre(model.getNombre()); // Asigna el nombre de la marca.
        dto.setFechaEliminacion(model.getFechaEliminacion()); // Asigna la fecha de eliminación (si existe).
        return dto; // Devuelve el DTO con los datos asignados.
    }

    /**
     * Convierte un objeto de tipo MarcaDTO en un objeto de tipo Marca.
     *
     * @param dto El objeto MarcaDTO a convertir.
     * @return Un objeto Marca con los mismos datos que el objeto MarcaDTO.
     */
    public static Marca toEntity(MarcaDTO dto) {
        Marca model = new Marca();
        model.setId(dto.getId()); // Asigna el ID del DTO al modelo.
        model.setNombre(dto.getNombre()); // Asigna el nombre del DTO al modelo.
        model.setFechaEliminacion(dto.getFechaEliminacion()); // Asigna la fecha de eliminación del DTO al modelo.
        return model; // Devuelve el modelo con los datos asignados.
    }
}
