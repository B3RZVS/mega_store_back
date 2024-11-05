package com.tpi_pais.mega_store.products.controller.productoController;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.dto.ProductoDTO; // Asegúrate de tener este DTO
import com.tpi_pais.mega_store.products.mapper.ProductoMapper; // Mapper para convertir a DTO
import com.tpi_pais.mega_store.products.model.Producto; // Modelo Producto
import com.tpi_pais.mega_store.products.service.IProductoService; // Servicio de Producto
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class GetProductoController {

    @Autowired
    private IProductoService productoService; // Servicio para manejar productos

    @Autowired
    private ResponseService responseService; // Servicio para respuestas

    @GetMapping("/productos") // Endpoint para obtener todos los productos
    public ResponseEntity<?> getAll() {
        List<ProductoDTO> productos = productoService.listar(); // Llama al servicio para listar productos
        if (productos.isEmpty()) {
            throw new BadRequestException("No hay productos creados");
        }
        return responseService.successResponse(productos, "OK"); // Retorna la respuesta con los productos
    }

    @GetMapping("/producto/{id}") // Endpoint para obtener un producto por ID
    public ResponseEntity<?> getPorId(@PathVariable Integer id) {
        Producto producto = productoService.buscarPorId(id); // Busca el producto por ID
        if (producto == null) {
            throw new BadRequestException("Producto no encontrado"); // Manejo de error si no se encuentra el producto
        }
        ProductoDTO productoDTO = ProductoMapper.toDTO(producto); // Convierte el modelo a DTO
        return responseService.successResponse(productoDTO, "OK"); // Retorna la respuesta con el producto DTO
    }
}
