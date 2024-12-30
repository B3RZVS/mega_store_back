package com.tpi_pais.mega_store.products.service;

import com.tpi_pais.mega_store.auth.model.Usuario;
import com.tpi_pais.mega_store.products.dto.HistorialPrecioDTO;
import com.tpi_pais.mega_store.products.model.HistorialPrecio;
import com.tpi_pais.mega_store.products.model.Producto;

import java.math.BigDecimal;
import java.util.List;

public interface IHistorialPrecioService {

    public void crear(BigDecimal precio, Producto producto, String token);

    public void verificarAtributos(HistorialPrecioDTO modelDto);

    public void verificarPrecio(BigDecimal precio);

    public Usuario obtenerUsuario(String token);

    public HistorialPrecio obtenerActual (Integer productoId);

    public List<HistorialPrecioDTO> listarPorProducto(Integer productoId);

    public List<HistorialPrecioDTO> listarPorUsuario(Integer usuarioId);

    public Producto obtenerProducto(Integer productoId);

}
