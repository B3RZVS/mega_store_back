package com.tpi_pais.mega_store.products.service;

import com.tpi_pais.mega_store.products.model.Marca;
import com.tpi_pais.mega_store.products.model.Producto;
import com.tpi_pais.mega_store.products.dto.ProductoDTO;
import java.util.List;

public interface IProductoService {
    List<ProductoDTO> listar(); // Lista todos los productos que no están eliminados

    Producto buscarPorId(Integer id); // Busca producto activo por ID

    Producto buscarPorNombre (String nombre);

    Producto buscarEliminadoPorId(Integer id); // Busca producto eliminado por ID

    ProductoDTO guardar(ProductoDTO productoDTO); // Guarda producto nuevo o recupera un eliminado

    Producto guardar(Producto producto); // Guarda producto sin DTO

    void eliminar(Producto producto, String usuario); // Elimina un producto y guarda el usuario que lo elimina

    void recuperar(Producto producto); // Recupera un producto eliminado

    ProductoDTO verificarAtributos(ProductoDTO productoDTO); // Verifica y valida los atributos antes de guardar

    boolean productoExistente(String nombre); // Verifica si un producto con el mismo nombre ya existe

    void actualizarStock(Producto producto, int cantidad, boolean esEntrada); // Actualiza stock del producto
}