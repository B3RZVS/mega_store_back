package com.tpi_pais.mega_store.products.mapper;

import com.tpi_pais.mega_store.products.dto.HistorialPrecioDTO;
import com.tpi_pais.mega_store.products.model.HistorialPrecio;
import org.springframework.stereotype.Component;

/**
 * Clase Mapper para la conversión entre el modelo de datos HistorialPrecio y el DTO HistorialPrecioDTO.
 * Facilita la transformación de los datos entre la capa de persistencia y la capa de presentación.
 */
@Component
public class HistorialPrecioMapper {

    /**
     * Constructor privado para evitar la creación de instancias de la clase.
     */
    private HistorialPrecioMapper() {}

    /**
     * Convierte un objeto de tipo HistorialPrecio en un objeto de tipo HistorialPrecioDTO.
     *
     * @param model El objeto HistorialPrecio a convertir.
     * @return Un objeto HistorialPrecioDTO con los mismos datos que el objeto HistorialPrecio.
     */
    public static HistorialPrecioDTO toDTO(HistorialPrecio model) {
        HistorialPrecioDTO dto = new HistorialPrecioDTO();
        dto.setId(model.getId()); // Asigna el ID del historial de precio.
        dto.setPrecio(model.getPrecio()); // Asigna el precio del historial.
        dto.setFecha(model.getFecha()); // Asigna la fecha del historial.
        dto.setUsuarioId(model.getUsuario().getId()); // Asigna el ID del usuario asociado.
        dto.setProductoId(model.getProducto().getId()); // Asigna el ID del producto asociado.
        return dto; // Devuelve el DTO creado.
    }

    /**
     * Convierte un objeto de tipo HistorialPrecioDTO en un objeto de tipo HistorialPrecio.
     *
     * @param dto El objeto HistorialPrecioDTO a convertir.
     * @return Un objeto HistorialPrecio con los mismos datos que el objeto HistorialPrecioDTO.
     */
    public static HistorialPrecio toEntity(HistorialPrecioDTO dto) {
        HistorialPrecio model = new HistorialPrecio();
        model.setId(dto.getId()); // Asigna el ID del historial de precio desde el DTO.
        model.setPrecio(dto.getPrecio()); // Asigna el precio desde el DTO.
        model.setFecha(dto.getFecha()); // Asigna la fecha desde el DTO.
        model.setUsuario(null); // Inicializa el usuario como null, ya que no es parte del DTO.
        model.setProducto(null); // Inicializa el producto como null, ya que no es parte del DTO.
        return model; // Devuelve el modelo convertido.
    }
}
