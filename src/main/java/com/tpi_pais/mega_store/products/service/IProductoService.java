package com.tpi_pais.mega_store.products.service;

import com.tpi_pais.mega_store.products.model.Producto;
import com.tpi_pais.mega_store.products.dto.ProductoDTO;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IProductoService {
    List<ProductoDTO> listar(); // Lista todos los productos que no están eliminados

    Producto buscarPorId(Integer id); // Busca producto activo por ID

    Producto buscarPorNombre (String nombre);

    Producto buscarEliminadoPorId(Integer id); // Busca producto eliminado por ID

    ProductoDTO crear (ProductoDTO productoDTO, String token);

    ProductoDTO guardar(ProductoDTO productoDTO, String token); // Guarda producto nuevo o recupera un eliminado

    Producto guardar(Producto producto); // Guarda producto sin DTO

    Producto actualizar(Producto producto);

    void eliminar(Producto producto, String usuario); // Elimina un producto y guarda el usuario que lo elimina

    void recuperar(Producto producto); // Recupera un producto eliminado

    ProductoDTO verificarAtributos(ProductoDTO productoDTO); // Verifica y valida los atributos antes de guardar

    boolean productoExistente(String nombre); // Verifica si un producto con el mismo nombre ya existe

    void actualizarStock(Producto producto, int cantidad, boolean esEntrada); // Actualiza stock del producto

    // Métodos de verificación de atributos individuales
    void verificarNombre(ProductoDTO productoDTO); // Verifica y formatea el nombre del producto

    void verificarDescripcion(String descripcion); // Verifica longitud de la descripción

    void verificarPrecio(BigDecimal precio); // Verifica que el precio sea mayor que 0

    void verificarPeso(BigDecimal peso); // Verifica que el peso sea mayor que 0

    void verificarStock(Integer stockMedio, Integer stockMinimo); // Verifica valores de stock mínimo y medio
    
    void verificarCategoria(Integer categoriaId); // Verifica existencia de la categoría

    void verificarSucursal(Integer[] sucursales); // Verifica existencia de la sucursal

    void verificarColor(Integer colorId); // Verifica existencia del color

    void verificarTalle(Integer talleId); // Verifica existencia del talle

    void verificarMarca(Integer marcaId);

    void verificarImagen(MultipartFile imagen);

    void actualizarPrecio(Producto producto, BigDecimal precio, String token);
}