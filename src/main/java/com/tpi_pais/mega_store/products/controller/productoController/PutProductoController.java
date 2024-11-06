package com.tpi_pais.mega_store.products.controller.productoController;

import com.tpi_pais.mega_store.exception.BadRequestException;
import com.tpi_pais.mega_store.exception.ResponseService;
import com.tpi_pais.mega_store.products.dto.ProductoDTO;
import com.tpi_pais.mega_store.products.model.Producto;
import com.tpi_pais.mega_store.products.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class PutProductoController {

    @Autowired
    private IProductoService productoService;

    @Autowired
    private ResponseService responseService;

    @PutMapping("/producto")
    public ResponseEntity<?> actualizar(@RequestBody ProductoDTO productoDTO) {
        // Buscar el producto que se quiere modificar por su ID
        Producto productoModificar = productoService.buscarPorId(productoDTO.getId());

        // Verificar atributos antes de proceder
        ProductoDTO productoDTOVerificado = productoService.verificarAtributos(productoDTO);

        // Validar si el nombre del producto ya existe
        if (productoService.productoExistente(productoDTOVerificado.getNombre())
                && !productoModificar.getNombre().equals(productoDTOVerificado.getNombre())) {
            throw new BadRequestException("Ya existe un producto con ese nombre");
        } else {
            // Actualizar y guardar el producto
            ProductoDTO productoGuardado = productoService.guardar(productoDTOVerificado);
            return responseService.successResponse(productoGuardado, "Producto actualizado");
        }
    }

    @PutMapping("/producto/recuperar/{id}")
    public ResponseEntity<?> recuperar(@PathVariable Integer id) {
        // Buscar el producto eliminado por ID
        Producto producto = productoService.buscarEliminadoPorId(id);

        // Recuperar el producto si estaba eliminado
        productoService.recuperar(producto);
        return responseService.successResponse(producto, "Producto recuperado");
    }
}
