package com.tpi_pais.mega_store.products.service;

import com.tpi_pais.mega_store.products.dto.MovimientoStockDTO;
import com.tpi_pais.mega_store.products.model.MovimientoStock;
import com.tpi_pais.mega_store.products.model.Producto;
import com.tpi_pais.mega_store.products.model.Sucursal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IMovimientoStockService {
    public ArrayList<MovimientoStockDTO> guardar(MovimientoStockDTO model);

    public MovimientoStockDTO guardar (Integer productoId, Integer cantidad, Boolean esEgreso);

    public void guardar (Producto producto, Integer[] sucursales);

    public List<MovimientoStockDTO> listarPorProducto(Integer productoId);

    public Integer obtenerStockActual(Integer productoId);

    public Integer obtenerStockActual(Producto producto);

    public void verificarCantidad (Integer cantidad, Boolean esEgreso, Producto producto);


    public ArrayList<MovimientoStockDTO> egreso (Producto producto, Integer cantidad);

    public ArrayList<MovimientoStockDTO> ingreso (Producto producto, Integer cantidad, Sucursal sucursal);
}
