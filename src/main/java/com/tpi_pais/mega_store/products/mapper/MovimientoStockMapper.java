package com.tpi_pais.mega_store.products.mapper;

import com.tpi_pais.mega_store.products.dto.MovimientoStockDTO;
import com.tpi_pais.mega_store.products.model.MovimientoStock;
import com.tpi_pais.mega_store.products.model.Producto;
import com.tpi_pais.mega_store.products.service.ProductoService;
import org.springframework.stereotype.Component;

/**
 * Clase Mapper para la conversión entre el modelo de datos MovimientoStock y el DTO MovimientoStockDTO.
 * Facilita la transformación de los datos entre la capa de persistencia y la capa de presentación.
 */
@Component
public class MovimientoStockMapper {

    private final ProductoService productoService;

    /**
     * Constructor de la clase. Se inyecta el servicio de Producto.
     *
     * @param productoService El servicio que permite acceder a los productos.
     */
    public MovimientoStockMapper(ProductoService productoService) {
        this.productoService = productoService;
    }

    /**
     * Convierte un objeto de tipo MovimientoStock en un objeto de tipo MovimientoStockDTO.
     *
     * @param model El objeto MovimientoStock a convertir.
     * @return Un objeto MovimientoStockDTO con los mismos datos que el objeto MovimientoStock.
     */
    public MovimientoStockDTO toDTO(MovimientoStock model) {
        MovimientoStockDTO dto = new MovimientoStockDTO();
        dto.setId(model.getId()); // Asigna el ID del movimiento de stock.
        dto.setCantidad(model.getCantidad()); // Asigna la cantidad del movimiento de stock.
        dto.setEsEgreso(model.getEsEgreso()); // Asigna si el movimiento es de egreso (salida) o ingreso (entrada).
        dto.setFechaCreacion(model.getFechaCreacion()); // Asigna la fecha de creación del movimiento.
        dto.setIdProducto(model.getProducto().getId()); // Asigna el ID del producto asociado al movimiento.
        return dto; // Devuelve el DTO creado.
    }

    /**
     * Convierte un objeto de tipo MovimientoStockDTO en un objeto de tipo MovimientoStock.
     *
     * @param dto El objeto MovimientoStockDTO a convertir.
     * @return Un objeto MovimientoStock con los mismos datos que el objeto MovimientoStockDTO.
     */
    public MovimientoStock toEntity(MovimientoStockDTO dto) {
        MovimientoStock model = new MovimientoStock();
        model.setCantidad(dto.getCantidad()); // Asigna la cantidad del DTO.
        model.esIngreso(); // Llama a un método de la entidad que marca si es un ingreso (por defecto).
        model.setFechaCreacion(dto.getFechaCreacion()); // Asigna la fecha de creación del DTO.
        model.setId(dto.getId()); // Asigna el ID del DTO.
        model.setProducto(this.productoService.buscarPorId(dto.getIdProducto())); // Busca el producto correspondiente por ID usando el servicio ProductoService.
        return model; // Devuelve el modelo convertido.
    }
}
