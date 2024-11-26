package com.tpi_pais.mega_store.products.mapper;


import com.tpi_pais.mega_store.products.dto.MovimientoStockDTO;
import com.tpi_pais.mega_store.products.model.MovimientoStock;
import com.tpi_pais.mega_store.products.model.Producto;
import com.tpi_pais.mega_store.products.service.ProductoService;

public class MovimientoStockMapper {
    public MovimientoStockDTO toDTO(MovimientoStock model) {
        MovimientoStockDTO dto = new MovimientoStockDTO();
        dto.setId(model.getId());
        dto.setCantidad(model.getCantidad());
        dto.setEsEgreso(model.getEsEgreso());
        dto.setFechaCreacion(model.getFechaCreacion());
        dto.setIdProducto(model.getProducto().getId());
        return dto;
    }

    public static MovimientoStock toEntity (MovimientoStockDTO dto){
        MovimientoStock model = new MovimientoStock();
        model.setCantidad(dto.getCantidad());
        model.esIngreso(dto.getEsEgreso());
        model.setFechaCreacion(dto.getFechaCreacion());
        model.setId(dto.getId());
        ProductoService productoService = new ProductoService();
        model.setProducto(productoService.buscarPorId(dto.getIdProducto()));
        return model;
    }
}
