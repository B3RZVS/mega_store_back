package com.tpi_pais.mega_store.products.mapper;

import com.tpi_pais.mega_store.products.dto.SucursalDTO;
import com.tpi_pais.mega_store.products.model.Sucursal;
import org.springframework.stereotype.Component;

/**
 * Clase Mapper para la conversión entre el modelo de datos Sucursal y el DTO SucursalDTO.
 * Facilita la transformación de los datos entre la capa de persistencia y la capa de presentación.
 */
@Component
public class SucursalMapper {

    /**
     * Constructor de la clase. No se necesita inicializar ningún componente adicional.
     */
    private SucursalMapper() {}

    /**
     * Convierte un objeto de tipo Sucursal en un objeto de tipo SucursalDTO.
     *
     * @param model El objeto Sucursal a convertir.
     * @return Un objeto SucursalDTO con los mismos datos que el objeto Sucursal.
     */
    public static SucursalDTO toDTO(Sucursal model) {
        SucursalDTO dto = new SucursalDTO();
        dto.setId(model.getId()); // Asigna el ID de la sucursal.
        dto.setNombre(model.getNombre()); // Asigna el nombre de la sucursal.
        dto.setFechaEliminacion(model.getFechaEliminacion()); // Asigna la fecha de eliminación (si existe).
        return dto; // Devuelve el DTO con los datos asignados.
    }

    /**
     * Convierte un objeto de tipo SucursalDTO en un objeto de tipo Sucursal.
     *
     * @param dto El objeto SucursalDTO a convertir.
     * @return Un objeto Sucursal con los mismos datos que el objeto SucursalDTO.
     */
    public static Sucursal toEntity(SucursalDTO dto) {
        Sucursal model = new Sucursal();
        model.setId(dto.getId()); // Asigna el ID del DTO al modelo.
        model.setNombre(dto.getNombre()); // Asigna el nombre del DTO al modelo.
        model.setFechaEliminacion(dto.getFechaEliminacion()); // Asigna la fecha de eliminación del DTO al modelo.
        return model; // Devuelve el modelo con los datos asignados.
    }
}
