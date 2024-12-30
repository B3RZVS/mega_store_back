package com.tpi_pais.mega_store.products.controller.productoController;

import com.tpi_pais.mega_store.exception.ResponseService; // Servicio para manejar respuestas
import com.tpi_pais.mega_store.products.model.Producto; // Modelo Producto
import com.tpi_pais.mega_store.products.service.IProductoService; // Servicio para manejar productos
import com.tpi_pais.mega_store.products.service.ProductoService; // Servicio de Producto
import com.tpi_pais.mega_store.utils.ApiResponse; // Respuesta API común
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products") // Ruta base para los productos
public class DeleteProductoController {

    private final IProductoService modelService; // Servicio para manejar productos (No debe ser estático)

    private final ResponseService responseService; // Servicio para manejar respuestas

    // Constructor con inyección de dependencias
    public DeleteProductoController(IProductoService modelService, ResponseService responseService) {
        this.modelService = modelService;
        this.responseService = responseService;
    }

    // Endpoint para eliminar un producto por su ID
    @DeleteMapping("/producto/{id}")
    public ResponseEntity<ApiResponse<Object>> eliminar(@PathVariable Integer id) {
        // Buscar el producto por su ID en el servicio
        Producto model = modelService.buscarPorId(id);

        // Llamar al servicio para eliminar el producto (con el usuario que realiza la eliminación)
        modelService.eliminar(model, "Usuario que lo realizo");

        // Retornar respuesta con el producto eliminado y un mensaje de éxito
        return responseService.successResponse(model, "Producto eliminado");
    }
}
