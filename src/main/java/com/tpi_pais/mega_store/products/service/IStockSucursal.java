package com.tpi_pais.mega_store.products.service;

import com.tpi_pais.mega_store.products.model.Producto;
import com.tpi_pais.mega_store.products.model.Sucursal;

import java.util.Map;

public interface IStockSucursal {

    public void guardar (Producto producto, Sucursal sucursal, Integer stock);

}
