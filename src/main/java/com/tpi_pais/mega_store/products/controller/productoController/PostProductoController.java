package com.tpi_pais.mega_store.products.controller.productoController;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.dto.ProductoDTO; // Asegúrate de tener este DTO
import com.tpi_pais.mega_store.products.model.Producto; // Modelo Producto
import com.tpi_pais.mega_store.products.service.IProductoService; // Servicio de Producto
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class PostProductoController {

    @Autowired
    private IProductoService productoService; // Servicio para manejar productos

    @Autowired
    private ResponseService responseService; // Servicio para respuestas

    @PostMapping("/producto")
    public ResponseEntity<?> guardar(@RequestBody ProductoDTO productoDTO) {
        // Verificar atributos antes de proceder
        productoDTO = productoService.verificarAtributos(productoDTO);

        // Validar si el nombre del producto ya existe
        if (productoService.productoExistente(productoDTO.getNombre())) {
            Producto aux = productoService.buscarPorNombre(productoDTO.getNombre());
            if (aux.esEliminado()) {
                productoService.recuperar(aux); // Recuperar si el producto estaba eliminado
                return responseService.successResponse(productoDTO, "El producto ya existía y ha sido recuperado");
            } else {
                throw new BadRequestException("Ya existe un producto con ese nombre");
            }
        } else {
            // Guardar nuevo producto
            ProductoDTO productoGuardado = productoService.guardar(productoDTO);
            return responseService.successResponse(productoGuardado, "Producto guardado");
        }
    }
}
