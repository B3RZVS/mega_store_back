package com.tpi_pais.mega_store.products.controller.productoController;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.dto.ProductoDTO; // DTO de Producto
import com.tpi_pais.mega_store.products.mapper.ProductoMapper; // Mapper para convertir a DTO
import com.tpi_pais.mega_store.products.model.Producto; // Modelo Producto
import com.tpi_pais.mega_store.products.service.IProductoService; // Servicio de Producto
import com.tpi_pais.mega_store.utils.ApiResponse; // Respuesta API común
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products") // Ruta base para los productos
public class GetProductoController {

    private final IProductoService productoService; // Servicio para manejar productos

    private final ResponseService responseService; // Servicio para manejar respuestas

    // Constructor con inyección de dependencias
    public GetProductoController(IProductoService productoService, ResponseService responseService) {
        this.productoService = productoService;
        this.responseService = responseService;
    }

    // Endpoint para obtener todos los productos
    @GetMapping("/productos")
    public ResponseEntity<ApiResponse<Object>> getAll() {
        // Llamar al servicio para listar todos los productos
        List<ProductoDTO> productos = productoService.listar();
        // Si no hay productos, lanzar una excepción con mensaje personalizado
        if (productos.isEmpty()) {
            throw new BadRequestException("No hay productos creados");
        }
        // Retornar respuesta con los productos en formato DTO y mensaje "OK"
        return responseService.successResponse(productos, "OK");
    }

    // Endpoint para obtener un producto por su ID
    @GetMapping("/producto/{id}")
    public ResponseEntity<ApiResponse<Object>> getPorId(@PathVariable Integer id) {
        // Buscar el producto por su ID en el servicio
        Producto producto = productoService.buscarPorId(id);
        // Si no se encuentra el producto, lanzar una excepción con mensaje personalizado
        if (producto == null) {
            throw new BadRequestException("Producto no encontrado");
        }
        // Convertir el modelo Producto a DTO utilizando el mapper
        ProductoDTO productoDTO = ProductoMapper.toDTO(producto);
        // Retornar respuesta con el producto en formato DTO y mensaje "OK"
        return responseService.successResponse(productoDTO, "OK");
    }
}
