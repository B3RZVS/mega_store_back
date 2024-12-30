package com.tpi_pais.mega_store.products.service;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.MessagesException;
import com.tpi_pais.mega_store.products.dto.DetalleVentaDTO;
import com.tpi_pais.mega_store.products.model.Producto;
import org.springframework.stereotype.Service;

@Service
public class DetalleVentaService implements IDetalleVentaService {
    private final ProductoService productoService;

    public DetalleVentaService(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Override
    public void verificarDetalle(DetalleVentaDTO modelDto) {
        Producto producto = productoService.buscarPorId(modelDto.getIdProducto());
        if (producto.esEliminado()) {
            throw new BadRequestException(MessagesException.OBJECTO_ELIMINADO+" en el producto "+producto.getNombre());
        }
        if (producto.getStockActual() < modelDto.getCantidad()) {
            throw new BadRequestException(MessagesException.STOCK_INSUFICIENTE+" en el producto "+producto.getNombre());
        }
    }
}
