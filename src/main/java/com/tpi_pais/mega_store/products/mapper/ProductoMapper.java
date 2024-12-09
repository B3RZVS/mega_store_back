package com.tpi_pais.mega_store.products.mapper;

import com.tpi_pais.mega_store.products.dto.ProductoDTO;
import com.tpi_pais.mega_store.products.model.*;
import org.springframework.stereotype.Component;

/**
 * Clase Mapper para la conversión entre el modelo de datos Producto y el DTO ProductoDTO.
 * Facilita la transformación de los datos entre la capa de persistencia y la capa de presentación.
 */
@Component
public class ProductoMapper {

    /**
     * Constructor de la clase. No se necesita inicializar ningún componente adicional.
     */
    private ProductoMapper() {}

    /**
     * Convierte un objeto de tipo Producto en un objeto de tipo ProductoDTO.
     *
     * @param model El objeto Producto a convertir.
     * @return Un objeto ProductoDTO con los mismos datos que el objeto Producto.
     */
    public static ProductoDTO toDTO(Producto model) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(model.getId()); // Asigna el ID del producto.
        dto.setNombre(model.getNombre()); // Asigna el nombre del producto.
        dto.setDescripcion(model.getDescripcion()); // Asigna la descripción del producto.
        dto.setPrecio(model.getPrecio()); // Asigna el precio del producto.
        dto.setPeso(model.getPeso()); // Asigna el peso del producto.
        dto.setStockMedio(model.getStockMedio()); // Asigna el stock medio del producto.
        dto.setStockMinimo(model.getStockMinimo()); // Asigna el stock mínimo del producto.
        dto.setStockActual(model.getStockActual()); // Asigna el stock actual del producto.
        dto.setFoto(model.getFoto()); // Asigna la foto del producto.

        // Si las entidades relacionadas existen, asigna sus IDs correspondientes al DTO.
        if (model.getCategoria() != null) {
            dto.setCategoriaId(model.getCategoria().getId());
        }
        if (model.getSucursal() != null) {
            dto.setSucursalId(model.getSucursal().getId());
        }
        if (model.getMarca() != null) {
            dto.setMarcaId(model.getMarca().getId());
        }
        if (model.getTalle() != null) {
            dto.setTalleId(model.getTalle().getId());
        }
        if (model.getColor() != null) {
            dto.setColorId(model.getColor().getId());
        }

        dto.setFechaEliminacion(model.getFechaEliminacion()); // Asigna la fecha de eliminación (si existe).
        return dto; // Devuelve el DTO con los datos asignados.
    }

    /**
     * Convierte un objeto de tipo ProductoDTO en un objeto de tipo Producto.
     *
     * @param dto El objeto ProductoDTO a convertir.
     * @return Un objeto Producto con los mismos datos que el objeto ProductoDTO.
     */
    public static Producto toEntity(ProductoDTO dto) {
        Producto model = new Producto();
        model.setId(dto.getId()); // Asigna el ID del DTO al modelo.
        model.setNombre(dto.getNombre()); // Asigna el nombre del DTO al modelo.
        model.setDescripcion(dto.getDescripcion()); // Asigna la descripción del DTO al modelo.
        model.setPrecio(dto.getPrecio()); // Asigna el precio del DTO al modelo.
        model.setPeso(dto.getPeso()); // Asigna el peso del DTO al modelo.
        model.setStockMedio(dto.getStockMedio()); // Asigna el stock medio del DTO al modelo.
        model.setStockMinimo(dto.getStockMinimo()); // Asigna el stock mínimo del DTO al modelo.
        model.setStockActual(dto.getStockActual()); // Asigna el stock actual del DTO al modelo.
        model.setFoto(dto.getFoto()); // Asigna la foto del DTO al modelo.
        model.setFechaEliminacion(dto.getFechaEliminacion()); // Asigna la fecha de eliminación del DTO al modelo.

        // Asume que existe un método de servicio para obtener las entidades relacionadas por ID.
        if (dto.getCategoriaId() != null) {
            Categoria categoria = new Categoria();
            categoria.setId(dto.getCategoriaId()); // Establece el ID de la categoría.
            model.setCategoria(categoria); // Asigna la categoría al producto.
        }
        if (dto.getSucursalId() != null) {
            Sucursal sucursal = new Sucursal();
            sucursal.setId(dto.getSucursalId()); // Establece el ID de la sucursal.
            model.setSucursal(sucursal); // Asigna la sucursal al producto.
        }
        if (dto.getMarcaId() != null) {
            Marca marca = new Marca();
            marca.setId(dto.getMarcaId()); // Establece el ID de la marca.
            model.setMarca(marca); // Asigna la marca al producto.
        }
        if (dto.getTalleId() != null) {
            Talle talle = new Talle();
            talle.setId(dto.getTalleId()); // Establece el ID del talle.
            model.setTalle(talle); // Asigna el talle al producto.
        }
        if (dto.getColorId() != null) {
            Color color = new Color();
            color.setId(dto.getColorId()); // Establece el ID del color.
            model.setColor(color); // Asigna el color al producto.
        }
        return model; // Devuelve el modelo con los datos asignados.
    }
}
