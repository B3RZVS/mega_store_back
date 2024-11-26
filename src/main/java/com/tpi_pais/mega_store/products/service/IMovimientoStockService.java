package com.tpi_pais.mega_store.products.service;

import com.tpi_pais.mega_store.products.dto.MovimientoStockDTO;
import com.tpi_pais.mega_store.products.model.MovimientoStock;
import com.tpi_pais.mega_store.products.model.Producto;

import java.util.List;

public interface IMovimientoStockService {
    public MovimientoStockDTO guardar(MovimientoStockDTO model);

    public MovimientoStockDTO guardar (Integer productoId, Integer cantidad, Boolean esEgreso);

    public List<MovimientoStockDTO> listarPorProducto(Integer productoId);

    public Integer obtenerStockActual(Integer productoId);

    public Integer obtenerStockActual(Producto producto);

    public void verificarCantidad (Integer cantidad, Boolean esEgreso, Producto producto);

}
