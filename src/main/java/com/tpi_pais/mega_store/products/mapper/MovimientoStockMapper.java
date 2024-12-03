package com.tpi_pais.mega_store.products.mapper;


import com.tpi_pais.mega_store.products.dto.MovimientoStockDTO;
import com.tpi_pais.mega_store.products.model.MovimientoStock;
import com.tpi_pais.mega_store.products.model.Producto;
import com.tpi_pais.mega_store.products.service.ProductoService;
import org.springframework.stereotype.Component;

@Component
public class MovimientoStockMapper {
    private final ProductoService productoService;

    public MovimientoStockMapper(ProductoService productoService) {
        this.productoService = productoService;
    }

    public MovimientoStockDTO toDTO(MovimientoStock model) {
        MovimientoStockDTO dto = new MovimientoStockDTO();
        dto.setId(model.getId());
        dto.setCantidad(model.getCantidad());
        dto.setEsEgreso(model.getEsEgreso());
        dto.setFechaCreacion(model.getFechaCreacion());
        dto.setIdProducto(model.getProducto().getId());
        return dto;
    }

    public MovimientoStock toEntity(MovimientoStockDTO dto){
        MovimientoStock model = new MovimientoStock();
        model.setCantidad(dto.getCantidad());
        model.esIngreso(dto.getEsEgreso());
        model.setFechaCreacion(dto.getFechaCreacion());
        model.setId(dto.getId());
        model.setProducto(this.productoService.buscarPorId(dto.getIdProducto()));
        return model;
    }
}
