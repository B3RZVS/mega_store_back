package com.tpi_pais.mega_store.products.mapper;

import com.tpi_pais.mega_store.products.dto.ProductoDTO;
import com.tpi_pais.mega_store.products.model.*;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {
    public static ProductoDTO toDTO(Producto model) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(model.getId());
        dto.setNombre(model.getNombre());
        dto.setDescripcion(model.getDescripcion());
        dto.setPrecio(model.getPrecio());
        dto.setPeso(model.getPeso());
        dto.setStockMedio(model.getStockMedio());
        dto.setStockMinimo(model.getStockMinimo());
        dto.setStockActual(model.getStockActual());
        dto.setFoto(model.getFoto());

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

        dto.setFechaEliminacion(model.getFechaEliminacion());
        return dto;
    }

    public static Producto toEntity(ProductoDTO dto) {
        Producto model = new Producto();
        model.setId(dto.getId());
        model.setNombre(dto.getNombre());
        model.setDescripcion(dto.getDescripcion());
        model.setPrecio(dto.getPrecio());
        model.setPeso(dto.getPeso());
        model.setStockMedio(dto.getStockMedio());
        model.setStockMinimo(dto.getStockMinimo());
        model.setStockActual(dto.getStockActual());
        model.setFoto(dto.getFoto());
        model.setFechaEliminacion(dto.getFechaEliminacion());

        // Asumimos que tienes un método de servicio para obtener las entidades relacionadas por ID
        if (dto.getCategoriaId() != null) {
            Categoria categoria = new Categoria();
            categoria.setId(dto.getCategoriaId());
            model.setCategoria(categoria);
        }
        if (dto.getSucursalId() != null) {
            Sucursal sucursal = new Sucursal();
            sucursal.setId(dto.getSucursalId());
            model.setSucursal(sucursal);
        }
        if (dto.getMarcaId() != null) {
            Marca marca = new Marca();
            marca.setId(dto.getMarcaId());
            model.setMarca(marca);
        }
        if (dto.getTalleId() != null) {
            Talle talle = new Talle();
            talle.setId(dto.getTalleId());
            model.setTalle(talle);
        }
        if (dto.getColorId() != null) {
            Color color = new Color();
            color.setId(dto.getColorId());
            model.setColor(color);
        }
        return model;
    }
}
