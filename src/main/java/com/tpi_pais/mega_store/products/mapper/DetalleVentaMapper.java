package com.tpi_pais.mega_store.products.mapper;


import com.tpi_pais.mega_store.products.dto.DetalleVentaDTO;
import com.tpi_pais.mega_store.products.model.DetalleVenta;
import org.springframework.stereotype.Component;

@Component
public class DetalleVentaMapper {
    private DetalleVentaMapper() {}

    public static DetalleVentaDTO toDTO(DetalleVenta model) {
        DetalleVentaDTO dto = new DetalleVentaDTO();
        dto.setId(model.getId());
        dto.setIdVenta(model.getVenta().getId());
        dto.setIdProducto(model.getProducto().getId());
        dto.setProducto(model.getProducto().getNombre());
        dto.setCantidad(model.getCantidad());
        dto.setPrecioUnitario(model.getPrecioUnitario());
        dto.setSubtotal(model.getSubtotal());
        return dto;
    }
    public static DetalleVenta toEntity(DetalleVentaDTO dto) {
        DetalleVenta entity = new DetalleVenta();
        entity.setId(dto.getId());
        entity.setVenta(null);
        entity.setProducto(null);
        entity.setCantidad(dto.getCantidad());
        entity.setPrecioUnitario(dto.getPrecioUnitario());
        entity.setSubtotal(dto.getSubtotal());
        return entity;
    }
}
